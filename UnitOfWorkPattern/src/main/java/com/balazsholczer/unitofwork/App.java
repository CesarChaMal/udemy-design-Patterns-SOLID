package com.balazsholczer.unitofwork;

/**
 * Unit of Work Pattern: maintains list of objects affected by transaction
 * 
 * Key Concepts:
 * - Maintains a list of objects affected by a business transaction
 * - Coordinates writing out changes and resolving concurrency problems
 * - Tracks changes to entities during business transaction
 * - Commits all changes as single atomic operation
 * 
 * Benefits:
 * - Ensures transactional consistency
 * - Optimizes database calls by batching operations
 * - Tracks entity changes automatically
 * - Provides rollback capabilities
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Unit of Work Pattern ===");
        System.out.println("Maintaining list of objects affected by transaction");
        System.out.println();
        
        UnitOfWork unitOfWork = new UnitOfWork();
        
        // Create new customers
        Customer customer1 = new Customer("1", "John Doe", "john@example.com");
        Customer customer2 = new Customer("2", "Jane Smith", "jane@example.com");
        
        // Register new entities
        unitOfWork.registerNew(customer1);
        unitOfWork.registerNew(customer2);
        
        // Modify existing customer
        customer1.setEmail("john.doe@example.com");
        unitOfWork.registerDirty(customer1);
        
        // Remove a customer
        unitOfWork.registerRemoved(customer2);
        
        System.out.println("Has changes: " + unitOfWork.hasChanges());
        
        // Commit all changes
        unitOfWork.commit();
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Transactional consistency");
        System.out.println("✅ Batched database operations");
        System.out.println("✅ Automatic change tracking");
        System.out.println("✅ Rollback capabilities");
    }
}