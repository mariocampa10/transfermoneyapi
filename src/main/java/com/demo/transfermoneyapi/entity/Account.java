package com.demo.transfermoneyapi.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Name has to contain at least 3 characters")
    @NotNull(message = "Name cannot be null")
    private String name;

    @Size(min = 2, message = "Currency has to contain at least 2 characters")
    @NotNull(message = "Currency cannot be null")
    private String currency;

    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;

    @NotNull(message = "Treasury flag cannot be null")
    private Boolean treasury;

    public Account() {
    }

    public Account(String name, String currency, BigDecimal balance, Boolean treasury) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.treasury = treasury;
    }

    // Getters and Setters
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