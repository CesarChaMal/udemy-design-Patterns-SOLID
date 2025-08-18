package com.balazsholczer.eventsourcing;

import java.math.BigDecimal;

public class MoneyDepositedEvent extends Event {
    private final BigDecimal amount;
    
    public MoneyDepositedEvent(String aggregateId, BigDecimal amount) {
        super(aggregateId);
        this.amount = amount;
    }
    
    public BigDecimal getAmount() { return amount; }
    
    @Override
    public String getEventType() { return "MoneyDeposited"; }
    
    @Override
    public String toString() {
        return "MoneyDepositedEvent{amount=" + amount + "}";
    }
}