package com.balazsholczer.apigateway;

/**
 * API Gateway Pattern: single entry point for microservices
 * 
 * Key Concepts:
 * - Provides single entry point for multiple microservices
 * - Handles cross-cutting concerns (auth, rate limiting, logging)
 * - Routes requests to appropriate backend services
 * - Aggregates responses from multiple services
 * 
 * Benefits:
 * - Simplified client interaction
 * - Centralized cross-cutting concerns
 * - Service discovery and routing
 * - Protocol translation and aggregation
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== API Gateway Pattern ===");
        System.out.println("Single entry point for microservices");
        System.out.println();
        
        APIGateway gateway = new APIGateway();
        
        // Register microservices
        gateway.registerService("/users", new UserService());
        gateway.registerService("/orders", new OrderService());
        
        // Authenticated request
        Request authRequest = new Request("/users/123", "GET");
        authRequest.addHeader("Authorization", "Bearer token123");
        
        Response response1 = gateway.routeRequest(authRequest);
        System.out.println("Response: " + response1);
        
        // Unauthenticated request
        Request unauthRequest = new Request("/orders/456", "GET");
        Response response2 = gateway.routeRequest(unauthRequest);
        System.out.println("Response: " + response2);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Single entry point for clients");
        System.out.println("✅ Centralized authentication and authorization");
        System.out.println("✅ Request routing to appropriate services");
        System.out.println("✅ Cross-cutting concerns handling");
    }
}