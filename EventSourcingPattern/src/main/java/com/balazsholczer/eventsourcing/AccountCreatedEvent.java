package com.balazsholczer.eventsourcing;

import java.math.BigDecimal;

public class AccountCreatedEvent extends Event {
    private final String accountHolder;
    private final BigDecimal initialBalance;
    
    public AccountCreatedEvent(String aggregateId, String accountHolder, BigDecimal initialBalance) {
        super(aggregateId);
        this.accountHolder = accountHolder;
        this.initialBalance = initialBalance;
    }
    
    public String getAccountHolder() { return accountHolder; }
    public BigDecimal getInitialBalance() { return initialBalance; }
    
    @Override
    public String getEventType() { return "AccountCreated"; }
    
    @Override
    public String toString() {
        return "AccountCreatedEvent{holder='" + accountHolder + "', balance=" + initialBalance + "}";
    }
}