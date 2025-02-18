package com.demo.transfermoneyapi.controller;

import com.demo.transfermoneyapi.dto.TransferRequest;
import com.demo.transfermoneyapi.entity.Account;
import com.demo.transfermoneyapi.service.AccountService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/add")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<Account>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transferMoney(@RequestBody TransferRequest request) {
        accountService.transferMoney(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transfer successful");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Error handling
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Bad Request");
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            errorResponse.put(field, violation.getMessage());
        });
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

