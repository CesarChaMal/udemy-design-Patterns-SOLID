package com.balazsholczer.dbperservice;

import java.math.BigDecimal;

/**
 * Database per Service Pattern: service data isolation
 * 
 * Key Concepts:
 * - Each microservice owns its private database
 * - Services cannot directly access other services' data
 * - Data consistency through service APIs and events
 * - Independent database technology choices per service
 * 
 * Benefits:
 * - Strong service boundaries and data encapsulation
 * - Independent scaling and technology choices
 * - Fault isolation between services
 * - Enables true microservices autonomy
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Database per Service Pattern ===");
        System.out.println("Service data isolation");
        System.out.println();
        
        // Each service has its own database
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        
        // User service operations (isolated to user database)
        System.out.println("--- User Service Operations ---");
        userService.createUser("U001", "John Doe", "john@example.com");
        UserDatabase.User user = userService.getUser("U001");
        System.out.println("Retrieved: " + user);
        
        // Order service operations (isolated to order database)
        System.out.println("\n--- Order Service Operations ---");
        orderService.createOrder("O001", "U001", BigDecimal.valueOf(299.99));
        OrderDatabase.Order order = orderService.getOrder("O001");
        System.out.println("Retrieved: " + order);
        
        // Demonstrate data isolation
        System.out.println("\n--- Data Isolation Demonstration ---");
        System.out.println("User service cannot access order data directly");
        System.out.println("Order service cannot access user data directly");
        System.out.println("Services must communicate through APIs");
        
        // Cross-service operation (would require API calls in real system)
        System.out.println("\n--- Cross-Service Operation ---");
        System.out.println("To get order with user details, OrderService would:");
        System.out.println("1. Get order from its database: " + order);
        System.out.println("2. Call UserService API to get user: " + user);
        System.out.println("3. Combine data for response");
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Strong service boundaries and data encapsulation");
        System.out.println("✅ Independent database per service");
        System.out.println("✅ Fault isolation between services");
        System.out.println("✅ Enables microservices autonomy");
    }
}