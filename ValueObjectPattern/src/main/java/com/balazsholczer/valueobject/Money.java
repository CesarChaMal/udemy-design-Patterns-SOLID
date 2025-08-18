package com.balazsholczer.valueobject;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class Money {
    
    private final BigDecimal amount;
    private final Currency currency;
    
    public Money(BigDecimal amount, Currency currency) {
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
    }
    
    public Money(double amount, String currencyCode) {
        this(BigDecimal.valueOf(amount), Currency.getInstance(currencyCode));
    }
    
    public BigDecimal getAmount() { return amount; }
    public Currency getCurrency() { return currency; }
    
    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }
    
    public Money subtract(Money other) {
        validateSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }
    
    public Money multiply(BigDecimal factor) {
        return new Money(amount.multiply(factor), currency);
    }
    
    private void validateSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot operate on different currencies");
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
    
    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }
}