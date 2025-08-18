package com.balazsholczer.saga;

/**
 * Saga Pattern: distributed transaction management
 * 
 * Key Concepts:
 * - Manages distributed transactions across multiple services
 * - Each step has corresponding compensation action
 * - Ensures eventual consistency in distributed systems
 * - Handles partial failures with rollback mechanism
 * 
 * Benefits:
 * - Distributed transaction coordination
 * - Automatic compensation on failures
 * - Eventual consistency guarantee
 * - Fault tolerance in microservices
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Saga Pattern ===");
        System.out.println("Distributed transaction management");
        System.out.println();
        
        // Create saga orchestrator
        SagaOrchestrator saga = new SagaOrchestrator();
        saga.addStep(new PaymentStep());
        saga.addStep(new InventoryStep());
        
        // Execute saga multiple times to show both success and failure
        for (int i = 1; i <= 3; i++) {
            System.out.println("--- Saga Execution #" + i + " ---");
            boolean success = saga.executeSaga();
            System.out.println("Result: " + (success ? "SUCCESS" : "FAILED"));
            System.out.println();
        }
        
        System.out.println("=== Benefits Demonstrated ===");
        System.out.println("✅ Distributed transaction coordination");
        System.out.println("✅ Automatic compensation on failures");
        System.out.println("✅ Eventual consistency guarantee");
        System.out.println("✅ Fault tolerance in microservices");
    }
}