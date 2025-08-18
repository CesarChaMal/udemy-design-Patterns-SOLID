package com.balazsholczer.businessobject;

import java.math.BigDecimal;

public class Account {
    private String accountId;
    private String accountHolder;
    private BigDecimal balance;
    private AccountType type;
    
    public Account(String accountId, String accountHolder, BigDecimal balance, AccountType type) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.type = type;
    }
    
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance = balance.add(amount);
        System.out.println("Account: Deposited " + amount + ", new balance: " + balance);
    }
    
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        BigDecimal minBalance = type.getMinimumBalance();
        if (balance.subtract(amount).compareTo(minBalance) < 0) {
            throw new IllegalStateException("Insufficient funds. Minimum balance: " + minBalance);
        }
        
        balance = balance.subtract(amount);
        System.out.println("Account: Withdrew " + amount + ", new balance: " + balance);
    }
    
    public BigDecimal calculateInterest() {
        BigDecimal interest = balance.multiply(type.getInterestRate());
        System.out.println("Account: Calculated interest: " + interest);
        return interest;
    }
    
    public String getAccountId() { return accountId; }
    public String getAccountHolder() { return accountHolder; }
    public BigDecimal getBalance() { return balance; }
    public AccountType getType() { return type; }
    
    @Override
    public String toString() {
        return "Account{id='" + accountId + "', holder='" + accountHolder + 
               "', balance=" + balance + ", type=" + type + "}";
    }
}