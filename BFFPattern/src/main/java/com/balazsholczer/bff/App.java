package com.balazsholczer.bff;

/**
 * Backends for Frontends (BFF) Pattern: frontend-specific APIs
 * 
 * Key Concepts:
 * - Creates separate backend services for different frontend types
 * - Optimizes API responses for specific client needs
 * - Aggregates data from multiple microservices
 * - Provides client-specific data transformation
 * 
 * Benefits:
 * - Optimized APIs for different client types
 * - Reduced network calls from clients
 * - Client-specific data aggregation
 * - Independent evolution of frontend APIs
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Backends for Frontends (BFF) Pattern ===");
        System.out.println("Frontend-specific APIs");
        System.out.println();
        
        // Shared microservices
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        
        // Frontend-specific BFFs
        MobileBFF mobileBFF = new MobileBFF(userService, orderService);
        WebBFF webBFF = new WebBFF(userService, orderService);
        
        String userId = "U123";
        
        // Mobile client gets optimized, minimal data
        System.out.println("--- Mobile Client Request ---");
        MobileBFF.MobileUserProfile mobileProfile = mobileBFF.getUserProfile(userId);
        System.out.println("Mobile response: " + mobileProfile);
        
        System.out.println("\n--- Web Client Request ---");
        // Web client gets detailed, comprehensive data
        WebBFF.WebDashboard webDashboard = webBFF.getDashboard(userId);
        System.out.println("Web response: " + webDashboard);
        
        System.out.println("\n--- API Optimization Comparison ---");
        System.out.println("Mobile API: Minimal data, optimized for bandwidth");
        System.out.println("Web API: Comprehensive data, rich user experience");
        System.out.println("Both APIs aggregate data from same microservices");
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Optimized APIs for different client types");
        System.out.println("✅ Reduced network calls through aggregation");
        System.out.println("✅ Client-specific data transformation");
        System.out.println("✅ Independent frontend API evolution");
    }
}