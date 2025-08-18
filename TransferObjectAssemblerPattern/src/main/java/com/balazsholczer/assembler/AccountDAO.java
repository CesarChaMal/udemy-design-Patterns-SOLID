package com.balazsholczer.assembler;

import java.math.BigDecimal;
import java.util.Map;

public class AccountDAO {
    
    private final Map<String, AccountTO> accountsByCustomer = Map.of(
        "1", new AccountTO("ACC-001", "1", BigDecimal.valueOf(1500.00), "CHECKING"),
        "2", new AccountTO("ACC-002", "2", BigDecimal.valueOf(2500.00), "SAVINGS")
    );
    
    public AccountTO findByCustomerId(String customerId) {
        System.out.println("AccountDAO: Finding account for customer " + customerId);
        return accountsByCustomer.get(customerId);
    }
}