package com.demo.transfermoneyapi;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TransferMoneyLoadTest extends Simulation {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";

    private static String fromAccountId;
    private static String toAccountId;

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .header(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);


    // Load Test Scenario
    private ScenarioBuilder transferScenario = scenario("Money Transfer Load Test")
            .exec(
                    http("Transfer Money")
                            .post("/accounts/transfer")
                            .body(StringBody(session ->
                                    String.format("""
                                                {
                                                    "fromAccountId": "%s",
                                                    "toAccountId": "%s",
                                                    "amount": 50.00
                                                }
                                            """, fromAccountId, toAccountId)
                            ))
                            .asJson()
                            .check(status().is(200))
            );

    // Create Tests Accounts
    @Override
    public void before() {
        try {
            fromAccountId = createAccount("Account A", "EUR", 1000, true);
            toAccountId = createAccount("Account B", "EUR", 500, true);
            System.out.println("Created Accounts: From = " + fromAccountId + ", To = " + toAccountId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test accounts", e);
        }
    }

    private String createAccount(String name, String currency, int balance, boolean treasury) throws Exception {
        String requestBody = String.format("""
                    {
                        "name": "%s",
                        "currency": "%s",
                        "balance": %d,
                        "treasury": %b
                    }
                """, name, currency, balance, treasury);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/accounts/add"))
                .header(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body().split("\"id\":")[1].split(",")[0].trim(); // Extracts ID from JSON
        } else {
            throw new RuntimeException("Error creating account: " + response.body());
        }
    }

    // Load Test Execution
    {
        setUp(
                transferScenario.injectOpen(
                        rampUsers(10).during(Duration.ofMinutes(1)),  // 1 min ramp-up
                        constantUsersPerSec(10.0 / 60).during(Duration.ofMinutes(9))) // 10 transactions/min constant
        ).protocols(httpProtocol);
    }
}