# 🚀 Transfer Money API + Load Testing (Spring Boot + MySQL + Gatling)

This project is a **REST API** built with **Spring Boot** that allows creating accounts and transferring money between them, following RESTful principles. Additionally, a **Gatling load test** is included to simulate transfers.

---

## 🛠 **1. Tech Stack**

- **Java 17**
- **Spring Boot 3.4.2**
- **Gradle** (Build Tool)
- **Docker** (Build Tool)- 
- **Gatling** (Java)

---

## 🏗 **2. Setting Up the Project**

### 📌 **A. Initialize Spring Boot Project**

1. Create a new Spring Boot project following https://spring.io/guides/gs/accessing-data-mysql

2. Add the following dependencies:
    - **Spring Web**
    - **Spring Data JPA**
    - **MySQL Driver**
    - **Docker Compose Support**
    - **TestContainers**

---

## 🗄 **3. Database Configuration**

### 📌 **A. Configure MySQL`**
The MySQL database is automatically managed by the framework. Simply ensure Docker is running on your local machine, and a new container for the database will be created automatically.

---

## 🔹 **4. Implementing the API**

### 📌 **A. Create the `Account` Entity**
The goal of this class is to represent the `Account` entity in the system. It is marked with the `@Entity` annotation, indicating that it is a data entity and will be mapped to a table in the database.

### 📌 **B. Create the `AccountRepository` Repository**
Next, create the repository to perform CRUD operations on the database for Account entities.

### 📌 **C. Implement the `AccountService` Service**
Implement the service, which contains the core business functionality. It will focus on three main operations:
- Creating a new account
- Retrieving the account list
- Performing a transaction between accounts

These operations will be used by the controllers to execute the requested actions.

### 📌 **D. Create the `AccountController` Controller**
The controller will implement the endpoints exposed to the API clients. These endpoints are:

| Method | Endpoint                 | Description                     |
| ------ | ------------------------ | ------------------------------- |
| POST   | `/api/accounts/add`      | Create a new account            |
| GET    | `/api/accounts/all`      | Get all accounts                |
| POST   | `/api/accounts/transfer` | Transfer money between accounts |

To see complete OpenApi specification, go to openapi.yaml.

---

### 📌 **E. Handle Validation Errors**
It is important to provide proper feedback to clients when bad requests occur or when they do not follow the defined rules. To achieve this, implement exception handlers that catch exceptions thrown by the framework and return user-friendly responses.

---

## 🚀 **5. Load Testing with Gatling**
We will use Gatling library for Java. This will allow to have all the code integrated into the same project.

### 📌 **A. Add Gatling Plugin and Dependencies (Gradle)**

```gradle
plugins {
    id 'io.gatling.gradle' version '3.13.4'
}

dependencies {
    testImplementation 'io.gatling:gatling-core-java:3.13.4'
    testImplementation 'io.gatling:gatling-http-java:3.13.4'
}
```
### 📌 **B.`TransferMoneyLoadTest` Test Script**
#### 1. Setup

- **Before Hook**:  
  The simulation's `before()` method creates two test accounts (a source and a destination) by sending HTTP POST requests to the `/accounts/add` endpoint. It extracts and stores their IDs for use in the transfer test.

#### 2. Test Scenario

- **Money Transfer Request**:  
  The simulation defines a scenario that sends a POST request to the `/accounts/transfer` endpoint. This request includes a JSON payload that transfers a fixed amount (50.00 EUR) from the source account to the destination account.

- **Response Validation**:  
  It checks that the response status code is 200, ensuring that the transfer is processed successfully.

#### 3. Load Injection

- **Ramp-Up and Constant Load**:  
  The load test is configured to:
   - **Ramp up 10 users over 1 minute**, and then
   - **Maintain a constant rate of 10 transactions per minute for 9 minutes.**

### 📌 **C. Running the API and triggering Load Test Script**

To run the API, execute the following command in your terminal:
```sh
./gradlew bootRun
```

In another terminal, run the Gatling load test with:
```sh
./gradlew gatlingRun
```
---

To see the report go to build/reports/gatling

## 🚀 **7. Potential Improvements**
- Transactions between accounts with different currencies
- Add more endpoints to manage accounts (i.e. remove or update)

