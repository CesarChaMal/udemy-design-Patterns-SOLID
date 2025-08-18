package com.balazsholczer.retry;

/**
 * Retry Pattern: automatic retry with backoff
 * 
 * Key Concepts:
 * - Automatically retries failed operations
 * - Implements exponential backoff strategy
 * - Configurable retry attempts and delays
 * - Handles transient failures gracefully
 * 
 * Benefits:
 * - Improved resilience to transient failures
 * - Configurable retry strategies
 * - Exponential backoff prevents overwhelming services
 * - Automatic failure recovery
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Retry Pattern ===");
        System.out.println("Automatic retry with backoff");
        System.out.println();
        
        // Create retry policy: 4 attempts, 100ms base delay, 2x backoff
        RetryPolicy policy = new RetryPolicy(4, 100, 2.0);
        RetryableOperation<String> retryableOp = new RetryableOperation<>(policy);
        
        // Test with service that has 30% success rate
        UnreliableService service = new UnreliableService(0.3);
        
        // Try multiple times to show different outcomes
        for (int test = 1; test <= 3; test++) {
            System.out.println("--- Test #" + test + " ---");
            service.reset();
            
            try {
                String result = retryableOp.execute(service::callService);
                System.out.println("Success: " + result);
            } catch (Exception e) {
                System.out.println("Final failure: " + e.getMessage());
            }
            
            System.out.println();
        }
        
        System.out.println("=== Benefits Demonstrated ===");
        System.out.println("✅ Automatic retry on transient failures");
        System.out.println("✅ Exponential backoff strategy");
        System.out.println("✅ Configurable retry policy");
        System.out.println("✅ Graceful handling of service instability");
    }
}