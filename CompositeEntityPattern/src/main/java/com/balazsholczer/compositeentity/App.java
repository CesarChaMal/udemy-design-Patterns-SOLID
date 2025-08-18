package com.balazsholczer.compositeentity;

/**
 * Composite Entity Pattern: represents graphs of objects as single entity
 * 
 * Key Concepts:
 * - Represents complex entity relationships as single coarse-grained object
 * - Manages dependent objects lifecycle and relationships
 * - Provides unified interface for complex object graphs
 * - Reduces network calls by treating related objects as single unit
 * 
 * Structure:
 * - CompositeEntity: manages coarse-grained object and provides client interface
 * - CoarseGrainedObject: contains dependent objects and business logic
 * - DependentObject: fine-grained objects managed by coarse-grained object
 * - Client: uses composite entity instead of individual objects
 * 
 * Benefits:
 * - Reduces network overhead in distributed systems
 * - Simplifies client interaction with complex object graphs
 * - Provides transactional consistency across related objects
 * - Encapsulates complex relationships and dependencies
 * 
 * Use Cases:
 * - EJB entity beans with dependent objects
 * - Complex domain models with aggregates
 * - Distributed systems with related object graphs
 * - ORM frameworks with entity relationships
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Composite Entity Pattern ===");
        System.out.println("Representing graphs of objects as single entity");
        System.out.println();
        
        System.out.println("=== Traditional Composite Entity ===");
        
        Client client = new Client();
        
        // Create customer with composite entity
        client.createCustomer("CUST-001", "John Doe", "john@example.com");
        
        // Add address through composite entity
        client.setCustomerAddress("123 Main St", "New York", "10001");
        
        // Add phones through composite entity
        client.addCustomerPhone("HOME", "555-1234");
        client.addCustomerPhone("MOBILE", "555-5678");
        client.addCustomerPhone("WORK", "555-9999");
        
        // Print customer data
        client.printCustomerData();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Update customer data
        client.updateCustomer("John Smith", "johnsmith@example.com");
        client.printCustomerData();
        
        System.out.println("\n=== Direct Object Graph Management ===");
        
        // Show what happens without composite entity (complex client code)
        System.out.println("Without Composite Entity - Client must manage multiple objects:");
        
        CoarseGrainedObject directCustomer = new CoarseGrainedObject("CUST-002", "Jane Doe", "jane@example.com");
        
        Address directAddress = new Address("456 Oak Ave", "Boston", "02101");
        directCustomer.setAddress(directAddress);
        
        Phone directPhone1 = new Phone("HOME", "617-1234");
        Phone directPhone2 = new Phone("MOBILE", "617-5678");
        directCustomer.addPhone(directPhone1);
        directCustomer.addPhone(directPhone2);
        
        System.out.println("Direct management result: " + directCustomer);
        
        System.out.println("\n=== Composite Entity Benefits ===");
        
        // Create another customer to demonstrate benefits
        Client client2 = new Client();
        client2.createCustomer("CUST-003", "Bob Johnson", "bob@example.com");
        client2.setCustomerAddress("789 Pine St", "Chicago", "60601");
        client2.addCustomerPhone("MOBILE", "312-1111");
        
        System.out.println("Composite Entity - Single interface for complex operations:");
        client2.printCustomerData();
        
        System.out.println("\n=== Pattern Comparison ===");
        System.out.println("Without Composite Entity:");
        System.out.println("- Client manages multiple fine-grained objects");
        System.out.println("- Multiple network calls for related data");
        System.out.println("- Complex relationship management");
        System.out.println("- Potential consistency issues");
        
        System.out.println("\nWith Composite Entity:");
        System.out.println("- Single coarse-grained interface");
        System.out.println("- Reduced network calls");
        System.out.println("- Simplified client code");
        System.out.println("- Transactional consistency");
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Unified interface for complex object graphs");
        System.out.println("✅ Reduced network overhead in distributed systems");
        System.out.println("✅ Simplified client interaction with related objects");
        System.out.println("✅ Encapsulated relationship management");
        System.out.println("✅ Transactional consistency across dependent objects");
    }
}