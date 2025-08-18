package com.balazsholczer.dtofactory;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Data Transfer Object Factory Pattern: factory for creating DTOs
 * 
 * Key Concepts:
 * - Centralizes DTO creation logic
 * - Provides consistent DTO instantiation
 * - Handles complex DTO construction scenarios
 * - Abstracts DTO creation from client code
 * 
 * Benefits:
 * - Consistent DTO creation
 * - Centralized construction logic
 * - Easy to extend with new DTO types
 * - Simplified client code
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Data Transfer Object Factory Pattern ===");
        System.out.println("Factory for creating DTOs");
        System.out.println();
        
        // Create customer DTO
        DTOFactory customerFactory = DTOFactory.getInstance("customer");
        CustomerDTO customer = (CustomerDTO) customerFactory.createDTO(Map.of(
            "id", "CUST-001",
            "name", "John Doe",
            "email", "john@example.com"
        ));
        System.out.println("Created: " + customer);
        
        // Create order DTO
        DTOFactory orderFactory = DTOFactory.getInstance("order");
        OrderDTO order = (OrderDTO) orderFactory.createDTO(Map.of(
            "orderId", "ORD-001",
            "customerId", "CUST-001",
            "total", BigDecimal.valueOf(299.99)
        ));
        System.out.println("Created: " + order);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Centralized DTO creation");
        System.out.println("✅ Consistent instantiation logic");
        System.out.println("✅ Type-safe factory selection");
        System.out.println("✅ Simplified client code");
    }
}