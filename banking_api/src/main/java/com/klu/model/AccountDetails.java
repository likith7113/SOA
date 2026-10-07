package com.klu.model;

import java.io.Serializable;

public class AccountDetails implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private double balance;
    private String currency;
    private String accountHolder;
    private String accountNumber;
    
    public AccountDetails() {
    }
    
    public AccountDetails(double balance, String currency, String accountHolder, String accountNumber) {
        this.balance = balance;
        this.currency = currency;
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
