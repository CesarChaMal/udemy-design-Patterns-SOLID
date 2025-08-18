package com.balazsholczer.assembler;

import java.util.Map;

public class CustomerDAO {
    
    private final Map<String, CustomerTO> customers = Map.of(
        "1", new CustomerTO("1", "John Doe", "john@example.com", "123 Main St"),
        "2", new CustomerTO("2", "Jane Smith", "jane@example.com", "456 Oak Ave")
    );
    
    public CustomerTO findById(String customerId) {
        System.out.println("CustomerDAO: Finding customer " + customerId);
        return customers.get(customerId);
    }
}