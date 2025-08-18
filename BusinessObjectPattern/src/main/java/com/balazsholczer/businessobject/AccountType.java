package com.balazsholczer.businessobject;

import java.math.BigDecimal;

public enum AccountType {
    SAVINGS(BigDecimal.valueOf(0.03), BigDecimal.valueOf(100)),
    CHECKING(BigDecimal.valueOf(0.01), BigDecimal.ZERO),
    PREMIUM(BigDecimal.valueOf(0.05), BigDecimal.valueOf(1000));
    
    private final BigDecimal interestRate;
    private final BigDecimal minimumBalance;
    
    AccountType(BigDecimal interestRate, BigDecimal minimumBalance) {
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }
    
    public BigDecimal getInterestRate() { return interestRate; }
    public BigDecimal getMinimumBalance() { return minimumBalance; }
}