package com.balazsholczer.timeout;

/**
 * Timeout Pattern: request timeout handling
 * 
 * Key Concepts:
 * - Sets maximum execution time for operations
 * - Prevents indefinite blocking on slow services
 * - Provides graceful handling of timeout scenarios
 * - Ensures system responsiveness and reliability
 * 
 * Benefits:
 * - Prevents system hang due to slow operations
 * - Improves system responsiveness
 * - Provides predictable behavior
 * - Essential for distributed systems resilience
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Timeout Pattern ===");
        System.out.println("Request timeout handling");
        System.out.println();
        
        TimeoutExecutor timeoutExecutor = new TimeoutExecutor();
        ExternalService service = new ExternalService();
        
        // Test fast operation (should succeed)
        System.out.println("--- Fast Operation Test ---");
        try {
            String result = timeoutExecutor.executeWithTimeout(service::fastOperation, 1000);
            System.out.println("Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Timeout: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Test slow operation (should timeout)
        System.out.println("\n--- Slow Operation Test ---");
        try {
            String result = timeoutExecutor.executeWithTimeout(service::slowOperation, 1000);
            System.out.println("Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Timeout: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Test failing operation (should fail quickly)
        System.out.println("\n--- Failing Operation Test ---");
        try {
            String result = timeoutExecutor.executeWithTimeout(service::failingOperation, 1000);
            System.out.println("Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Timeout: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        timeoutExecutor.shutdown();
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Prevented system hang on slow operations");
        System.out.println("✅ Provided predictable timeout behavior");
        System.out.println("✅ Maintained system responsiveness");
        System.out.println("✅ Essential distributed systems resilience");
    }
}