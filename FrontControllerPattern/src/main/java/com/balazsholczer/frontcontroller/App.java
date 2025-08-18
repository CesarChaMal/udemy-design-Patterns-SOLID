package com.balazsholczer.frontcontroller;

/**
 * Front Controller Pattern: centralized request handling for web applications
 * 
 * Key Concepts:
 * - Single entry point for all requests
 * - Centralizes common request processing logic
 * - Dispatches requests to appropriate handlers
 * - Provides consistent pre/post processing
 * 
 * Structure:
 * - FrontController: single entry point for all requests
 * - Dispatcher: routes requests to appropriate handlers
 * - Command: handles specific request types
 * - Request/Response: encapsulate HTTP data
 * 
 * Benefits:
 * - Centralized request handling and routing
 * - Consistent security, logging, and error handling
 * - Simplified URL mapping and navigation
 * - Easier to add cross-cutting concerns
 * 
 * Use Cases:
 * - Web applications with multiple pages/endpoints
 * - REST API gateways
 * - Servlet-based Java web applications
 * - Microservices with centralized routing
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Front Controller Pattern ===");
        System.out.println("Centralized request handling for web applications");
        System.out.println();
        
        System.out.println("=== Traditional Front Controller ===");
        
        FrontController frontController = new FrontController();
        
        // Simulate web requests
        Request homeRequest = new Request("/", "GET");
        Response homeResponse = frontController.processRequest(homeRequest);
        System.out.println("Home Response: " + homeResponse.getContent());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        Request userRequest = new Request("/user", "GET");
        userRequest.setParameter("id", "123");
        Response userResponse = frontController.processRequest(userRequest);
        System.out.println("User Response: " + userResponse.getContent());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        Request notFoundRequest = new Request("/nonexistent", "GET");
        Response notFoundResponse = frontController.processRequest(notFoundRequest);
        System.out.println("404 Response: " + notFoundResponse.getContent());
        
        System.out.println("\n=== Functional Front Controller ===");
        
        FunctionalFrontController funcController = new FunctionalFrontController();
        
        Request apiRequest = new Request("/api/users", "GET");
        Response apiResponse = funcController.processRequest(apiRequest);
        System.out.println("API Response: " + apiResponse.getContent());
        
        System.out.println("\n=== Dynamic Command Registration ===");
        
        // Register new command dynamically
        frontController.registerCommand("/products", (req, res) -> {
            System.out.println("ProductCommand: Processing products request");
            res.setContent("<html><body><h1>Product Catalog</h1></body></html>");
        });
        
        Request productRequest = new Request("/products", "GET");
        Response productResponse = frontController.processRequest(productRequest);
        System.out.println("Product Response: " + productResponse.getContent());
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Single entry point for all requests");
        System.out.println("✅ Centralized pre/post processing");
        System.out.println("✅ Consistent request routing and handling");
        System.out.println("✅ Easy to add new endpoints dynamically");
        System.out.println("✅ Common cross-cutting concerns (logging, timing)");
    }
}