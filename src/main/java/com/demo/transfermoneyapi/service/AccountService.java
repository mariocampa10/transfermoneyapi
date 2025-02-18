package com.demo.transfermoneyapi.service;

import com.demo.transfermoneyapi.entity.Account;
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
            throw new IllegalArgumentException("Non treasury accounts can't be negative.");
        }
        return accountRepository.save(account);
    }

    // Gets existing accounts
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Original account not found."));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found."));

        // Currency has to be the same
        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            throw new IllegalArgumentException("Account currency does not match.");
        }

        // Amount has to be positive
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount can't be negative.");
        }

        BigDecimal newFromBalance = fromAccount.getBalance().subtract(amount);

        // Non treasury accounts can not have negative balance
        if (!fromAccount.getTreasury() && newFromBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Non treasury accounts can't be negative.");
        }

        fromAccount.setBalance(newFromBalance);
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}

