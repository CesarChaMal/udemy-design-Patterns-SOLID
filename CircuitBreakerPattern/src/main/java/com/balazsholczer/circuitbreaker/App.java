package com.balazsholczer.circuitbreaker;

/**
 * Circuit Breaker Pattern: fault tolerance for service calls
 * 
 * Key Concepts:
 * - Prevents cascading failures in distributed systems
 * - Monitors service calls and opens circuit when failures exceed threshold
 * - Provides fail-fast behavior when service is down
 * - Automatically attempts to close circuit when service recovers
 * 
 * States:
 * - CLOSED: Normal operation, calls pass through
 * - OPEN: Circuit is open, calls fail immediately
 * - HALF_OPEN: Testing if service has recovered
 * 
 * Benefits:
 * - Prevents resource exhaustion
 * - Improves system resilience
 * - Provides graceful degradation
 * - Automatic recovery detection
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Circuit Breaker Pattern ===");
        System.out.println("Fault tolerance for service calls");
        System.out.println();
        
        ExternalService service = new ExternalService();
        CircuitBreaker circuitBreaker = new CircuitBreaker(3, 2000); // 3 failures, 2 second timeout
        
        // Simulate multiple service calls
        for (int i = 1; i <= 15; i++) {
            System.out.println("\n--- Call #" + i + " ---");
            System.out.println("Circuit state: " + circuitBreaker.getState());
            
            try {
                String result = circuitBreaker.call(service::callService);
                System.out.println("Result: " + result);
            } catch (CircuitBreakerException e) {
                System.out.println("Circuit breaker error: " + e.getMessage());
            }
            
            // Add delay to simulate timeout
            if (i == 10) {
                System.out.println("\nWaiting for circuit breaker timeout...");
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        System.out.println("\nFinal circuit state: " + circuitBreaker.getState());
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Prevents cascading failures");
        System.out.println("✅ Fail-fast behavior when service is down");
        System.out.println("✅ Automatic recovery detection");
        System.out.println("✅ System resilience and graceful degradation");
    }
}