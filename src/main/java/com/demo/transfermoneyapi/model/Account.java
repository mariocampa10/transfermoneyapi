package com.demo.transfermoneyapi.model;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String currency;

    private BigDecimal balance;

    private Boolean treasury;

    public Account() {
    }

    public Account(String name, String currency, BigDecimal balance, Boolean treasury) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.treasury = treasury;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Boolean getTreasury() {
        return treasury;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setTreasury(Boolean treasury) {
        this.treasury = treasury;
    }
}