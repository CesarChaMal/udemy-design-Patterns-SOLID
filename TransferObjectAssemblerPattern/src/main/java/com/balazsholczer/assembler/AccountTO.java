package com.balazsholczer.assembler;

import java.math.BigDecimal;

public class AccountTO {
    private String accountId;
    private String customerId;
    private BigDecimal balance;
    private String accountType;
    
    public AccountTO() {}
    
    public AccountTO(String accountId, String customerId, BigDecimal balance, String accountType) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.balance = balance;
        this.accountType = accountType;
    }
    
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    
    @Override
    public String toString() {
        return "AccountTO{id='" + accountId + "', balance=" + balance + ", type='" + accountType + "'}";
    }
}