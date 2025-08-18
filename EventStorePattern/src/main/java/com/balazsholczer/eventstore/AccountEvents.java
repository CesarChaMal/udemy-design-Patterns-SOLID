package com.balazsholczer.eventstore;

import java.math.BigDecimal;

public class AccountEvents {
    
    public static class AccountOpened {
        public final String accountId;
        public final String accountHolder;
        public final BigDecimal initialBalance;
        
        public AccountOpened(String accountId, String accountHolder, BigDecimal initialBalance) {
            this.accountId = accountId;
            this.accountHolder = accountHolder;
            this.initialBalance = initialBalance;
        }
        
        @Override
        public String toString() {
            return "AccountOpened{accountId='" + accountId + "', accountHolder='" + 
                   accountHolder + "', initialBalance=" + initialBalance + "}";
        }
    }
    
    public static class MoneyDeposited {
        public final String accountId;
        public final BigDecimal amount;
        
        public MoneyDeposited(String accountId, BigDecimal amount) {
            this.accountId = accountId;
            this.amount = amount;
        }
        
        @Override
        public String toString() {
            return "MoneyDeposited{accountId='" + accountId + "', amount=" + amount + "}";
        }
    }
    
    public static class MoneyWithdrawn {
        public final String accountId;
        public final BigDecimal amount;
        
        public MoneyWithdrawn(String accountId, BigDecimal amount) {
            this.accountId = accountId;
            this.amount = amount;
        }
        
        @Override
        public String toString() {
            return "MoneyWithdrawn{accountId='" + accountId + "', amount=" + amount + "}";
        }
    }
}