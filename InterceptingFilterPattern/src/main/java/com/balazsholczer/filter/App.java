package com.balazsholczer.filter;

/**
 * Intercepting Filter Pattern: pre/post processing of requests and responses
 * 
 * Key Concepts:
 * - Intercepts requests before they reach the target resource
 * - Applies cross-cutting concerns (security, logging, compression)
 * - Chain of filters processes requests in sequence
 * - Each filter can modify request/response or stop processing
 * 
 * Structure:
 * - Filter: defines filtering interface
 * - FilterChain: manages filter execution sequence
 * - ConcreteFilter: implements specific filtering logic
 * - Target: final destination after all filters
 * 
 * Benefits:
 * - Separates cross-cutting concerns from business logic
 * - Configurable filter chains for different scenarios
 * - Reusable filters across different applications
 * - Clean separation of pre/post processing logic
 * 
 * Use Cases:
 * - Web application security (authentication, authorization)
 * - Request/response logging and auditing
 * - Data compression and encoding
 * - Input validation and sanitization
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Intercepting Filter Pattern ===");
        System.out.println("Pre/post processing of requests and responses");
        System.out.println();
        
        System.out.println("=== Traditional Filter Chain ===");
        
        // Create filter chain
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new LoggingFilter());
        filterChain.addFilter(new AuthenticationFilter());
        filterChain.addFilter(new CompressionFilter());
        
        // Test authenticated request
        Request authRequest = new Request("/api/users", "GET");
        authRequest.setHeader("Authorization", "Bearer token123");
        authRequest.setHeader("Accept-Encoding", "gzip");
        
        Response authResponse = new Response();
        
        System.out.println("Processing authenticated request:");
        filterChain.doFilter(authRequest, authResponse);
        System.out.println("Result: " + authResponse);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test unauthenticated request
        filterChain.reset();
        Request unauthRequest = new Request("/api/orders", "GET");
        Response unauthResponse = new Response();
        
        System.out.println("Processing unauthenticated request:");
        filterChain.doFilter(unauthRequest, unauthResponse);
        System.out.println("Result: " + unauthResponse);
        
        System.out.println("\n=== Functional Filter Chain ===");
        
        FunctionalFilterChain funcChain = new FunctionalFilterChain();
        funcChain.addFilter(FunctionalFilterChain.loggingFilter());
        funcChain.addFilter(FunctionalFilterChain.authFilter());
        funcChain.addFilter(FunctionalFilterChain.corsFilter());
        
        // Test with functional filters
        Request funcRequest = new Request("/api/products", "GET");
        funcRequest.setHeader("Authorization", "Bearer functional_token");
        Response funcResponse = new Response();
        
        System.out.println("Processing with functional filters:");
        funcChain.process(funcRequest, funcResponse);
        System.out.println("Result: " + funcResponse);
        System.out.println("CORS Header: " + funcResponse.getHeader("Access-Control-Allow-Origin"));
        
        System.out.println("\n=== Custom Functional Filters ===");
        
        FunctionalFilterChain customChain = new FunctionalFilterChain();
        
        // Add custom lambda filters
        customChain.addFilter((req, res) -> {
            System.out.println("Custom: Rate limiting check");
            req.setAttribute("rateLimit", "passed");
        });
        
        customChain.addFilter((req, res) -> {
            System.out.println("Custom: Request validation");
            if (req.getUri().contains("admin") && req.getAttribute("user") == null) {
                res.setStatusCode(403);
                res.setContent("Forbidden");
            }
        });
        
        Request customRequest = new Request("/admin/settings", "GET");
        Response customResponse = new Response();
        
        customChain.process(customRequest, customResponse);
        System.out.println("Custom result: " + customResponse);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Cross-cutting concerns separated from business logic");
        System.out.println("✅ Configurable filter chains for different scenarios");
        System.out.println("✅ Reusable filters (logging, auth, compression)");
        System.out.println("✅ Request/response modification capabilities");
        System.out.println("✅ Early termination on authentication/validation failures");
    }
}