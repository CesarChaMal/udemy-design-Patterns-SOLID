package com.balazsholczer.anticorruption;

import java.util.HashMap;
import java.util.Map;

public class LegacyCustomerService {
    private final Map<String, LegacyCustomer> customers = new HashMap<>();
    
    public LegacyCustomerService() {
        // Initialize with legacy data
        customers.put("CUST001", new LegacyCustomer("CUST001", "John Doe", "john@example.com", "555-1234", 1));
        customers.put("CUST002", new LegacyCustomer("CUST002", "Jane Smith", "jane@example.com", "555-5678", 0));
    }
    
    public LegacyCustomer getCustomerById(String custId) {
        System.out.println("LegacyCustomerService: Fetching customer " + custId);
        return customers.get(custId);
    }
    
    public void updateCustomerStatus(String custId, int statusCode) {
        System.out.println("LegacyCustomerService: Updating customer " + custId + " status to " + statusCode);
        LegacyCustomer customer = customers.get(custId);
        if (customer != null) {
            customer.status_code = statusCode;
        }
    }
}