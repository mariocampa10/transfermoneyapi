package com.demo.transfermoneyapi.service;

import com.demo.transfermoneyapi.model.Account;
import com.demo.transfermoneyapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Creates a new account.
    public Account createAccount(Account account) {
        if (!account.getTreasury() && account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Las cuentas no de tesorería no pueden tener saldo negativo.");
        }
        return accountRepository.save(account);
    }

    // Gets existing accounts
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}

