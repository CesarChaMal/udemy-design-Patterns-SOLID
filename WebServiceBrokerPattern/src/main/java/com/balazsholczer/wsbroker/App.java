package com.balazsholczer.wsbroker;

/**
 * Web Service Broker Pattern: broker for web service interactions
 * 
 * Key Concepts:
 * - Provides unified interface for multiple web services
 * - Handles service discovery and invocation
 * - Manages service registration and lifecycle
 * - Abstracts client from service implementation details
 * 
 * Benefits:
 * - Simplified service access
 * - Centralized service management
 * - Service abstraction and decoupling
 * - Error handling and fault tolerance
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Web Service Broker Pattern ===");
        System.out.println("Broker for web service interactions");
        System.out.println();
        
        WebServiceBroker broker = new WebServiceBroker();
        
        // Register services
        broker.registerService("payment", new PaymentService());
        broker.registerService("notification", new NotificationService());
        
        // Invoke services through broker
        String paymentResponse = broker.invokeService("payment", "{\"amount\": 100.00}");
        System.out.println("Payment response: " + paymentResponse);
        
        String notificationResponse = broker.invokeService("notification", "{\"message\": \"Payment processed\"}");
        System.out.println("Notification response: " + notificationResponse);
        
        // Try non-existent service
        String errorResponse = broker.invokeService("unknown", "{}");
        System.out.println("Error response: " + errorResponse);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Unified service interface");
        System.out.println("✅ Service discovery and management");
        System.out.println("✅ Error handling and fault tolerance");
        System.out.println("✅ Client-service decoupling");
    }
}