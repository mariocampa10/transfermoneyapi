package com.demo.transfermoneyapi.controller;

import com.demo.transfermoneyapi.dto.TransferRequest;
import com.demo.transfermoneyapi.model.Account;
import com.demo.transfermoneyapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    // Endpoint to create a new account
    @PostMapping(path="/add")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Account> getAllUsers() {
        // This returns a JSON or XML with the users
        return accountService.getAllAccounts();
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        accountService.transferMoney(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        return ResponseEntity.ok("Transfer successful.");
    }
}

