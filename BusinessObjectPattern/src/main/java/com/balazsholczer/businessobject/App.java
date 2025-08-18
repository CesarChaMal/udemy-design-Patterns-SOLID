package com.balazsholczer.businessobject;

import java.math.BigDecimal;

/**
 * Business Object Pattern: encapsulates business data and logic
 * 
 * Key Concepts:
 * - Combines data and business logic in single object
 * - Encapsulates business rules and validation
 * - Provides domain-specific operations
 * - Maintains business invariants
 * 
 * Benefits:
 * - Centralized business logic
 * - Data integrity through encapsulation
 * - Domain-driven design compliance
 * - Reusable business operations
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Business Object Pattern ===");
        System.out.println("Encapsulating business data and logic");
        System.out.println();
        
        // Create different account types
        Account savings = new Account("SAV-001", "John Doe", BigDecimal.valueOf(1000), AccountType.SAVINGS);
        Account checking = new Account("CHK-001", "Jane Smith", BigDecimal.valueOf(500), AccountType.CHECKING);
        
        System.out.println("Created accounts:");
        System.out.println(savings);
        System.out.println(checking);
        
        System.out.println("\n--- Business Operations ---");
        
        // Deposit operations
        savings.deposit(BigDecimal.valueOf(200));
        checking.deposit(BigDecimal.valueOf(100));
        
        // Withdrawal operations
        savings.withdraw(BigDecimal.valueOf(150));
        checking.withdraw(BigDecimal.valueOf(300));
        
        // Interest calculations
        BigDecimal savingsInterest = savings.calculateInterest();
        BigDecimal checkingInterest = checking.calculateInterest();
        
        System.out.println("\n--- Business Rule Enforcement ---");
        
        try {
            // Try to withdraw below minimum balance
            savings.withdraw(BigDecimal.valueOf(1000)); // Should fail
        } catch (IllegalStateException e) {
            System.out.println("Business rule enforced: " + e.getMessage());
        }
        
        try {
            // Try invalid deposit
            checking.deposit(BigDecimal.valueOf(-50)); // Should fail
        } catch (IllegalArgumentException e) {
            System.out.println("Business rule enforced: " + e.getMessage());
        }
        
        System.out.println("\nFinal account states:");
        System.out.println(savings);
        System.out.println(checking);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Centralized business logic");
        System.out.println("✅ Business rule enforcement");
        System.out.println("✅ Data integrity through encapsulation");
        System.out.println("✅ Domain-specific operations");
    }
}