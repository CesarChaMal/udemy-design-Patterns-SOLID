package com.balazsholczer.eventsourcing;

import java.math.BigDecimal;
import java.util.List;

public class BankAccount {
    private String accountId;
    private String accountHolder;
    private BigDecimal balance;
    
    public static BankAccount fromEvents(String accountId, List<Event> events) {
        BankAccount account = new BankAccount();
        account.accountId = accountId;
        
        for (Event event : events) {
            account.apply(event);
        }
        
        return account;
    }
    
    private void apply(Event event) {
        if (event instanceof AccountCreatedEvent) {
            AccountCreatedEvent created = (AccountCreatedEvent) event;
            this.accountHolder = created.getAccountHolder();
            this.balance = created.getInitialBalance();
        } else if (event instanceof MoneyDepositedEvent) {
            MoneyDepositedEvent deposited = (MoneyDepositedEvent) event;
            this.balance = this.balance.add(deposited.getAmount());
        }
    }
    
    public String getAccountId() { return accountId; }
    public String getAccountHolder() { return accountHolder; }
    public BigDecimal getBalance() { return balance; }
    
    @Override
    public String toString() {
        return "BankAccount{id='" + accountId + "', holder='" + accountHolder + "', balance=" + balance + "}";
    }
}