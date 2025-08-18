package com.balazsholczer.sidecar;

/**
 * Sidecar Pattern: service mesh and cross-cutting concerns
 * 
 * Key Concepts:
 * - Deploys helper services alongside main application
 * - Handles cross-cutting concerns (logging, metrics, security)
 * - Provides shared functionality without code changes
 * - Enables service mesh architecture
 * 
 * Benefits:
 * - Separation of business logic from infrastructure concerns
 * - Reusable cross-cutting functionality
 * - Language-agnostic infrastructure services
 * - Simplified service mesh implementation
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Sidecar Pattern ===");
        System.out.println("Service mesh and cross-cutting concerns");
        System.out.println();
        
        // Create main application with sidecars
        MainApplication app = new MainApplication("UserService");
        
        // Start application and sidecars
        app.start();
        
        System.out.println("\n--- Processing Requests ---");
        
        // Process some requests (sidecars handle cross-cutting concerns)
        app.processRequest("GET /users/123");
        app.processRequest("POST /users");
        app.processRequest("PUT /users/123");
        
        System.out.println("\n--- Shutdown ---");
        
        // Stop application and sidecars
        app.stop();
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Separation of business logic from infrastructure");
        System.out.println("✅ Reusable cross-cutting functionality");
        System.out.println("✅ Language-agnostic infrastructure services");
        System.out.println("✅ Service mesh architecture enablement");
    }
}