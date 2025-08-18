package com.balazsholczer.businessdelegate;

/**
 * Business Delegate Pattern: decouples presentation tier from business services
 * 
 * Key Concepts:
 * - Provides unified interface to business tier services
 * - Hides complexity of business service lookup and invocation
 * - Reduces coupling between presentation and business tiers
 * - Centralizes business service access logic
 * 
 * Structure:
 * - BusinessDelegate: provides simplified interface to business services
 * - BusinessLookup: locates and returns appropriate business service
 * - BusinessService: actual business logic implementation
 * - Client: uses BusinessDelegate instead of direct service calls
 * 
 * Benefits:
 * - Decouples presentation from business tier
 * - Simplifies client code by hiding service complexity
 * - Centralizes service lookup and caching logic
 * - Enables service substitution without client changes
 * 
 * Use Cases:
 * - J2EE applications with multiple business services
 * - Microservices architecture with service discovery
 * - Enterprise applications with complex business logic
 * - Systems requiring service abstraction and loose coupling
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Business Delegate Pattern ===");
        System.out.println("Decoupling presentation tier from business services");
        System.out.println();
        
        // Traditional approach
        Client client = new Client();
        
        System.out.println("Client processing order through Business Delegate:");
        client.processOrder("ORDER-123: 2x Laptops");
        
        System.out.println("\nClient processing payment through Business Delegate:");
        client.processPayment("PAYMENT-456: $2000");
        
        System.out.println("\n=== Functional Business Delegate ===");
        
        FunctionalBusinessDelegate funcDelegate = new FunctionalBusinessDelegate();
        
        String orderResult = funcDelegate.processRequest("order", "ORDER-789: 1x Phone");
        System.out.println("Functional result: " + orderResult);
        
        String paymentResult = funcDelegate.processRequest("payment", "PAYMENT-101: $500");
        System.out.println("Functional result: " + paymentResult);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Client doesn't know about specific business services");
        System.out.println("✅ Business service lookup is centralized");
        System.out.println("✅ Easy to add new services without changing client");
        System.out.println("✅ Presentation tier is decoupled from business tier");
    }
}