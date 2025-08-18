package com.balazsholczer.bulkhead;

import java.util.concurrent.Future;

/**
 * Bulkhead Pattern: resource isolation for fault tolerance
 * 
 * Key Concepts:
 * - Isolates resources to prevent cascading failures
 * - Separate thread pools for different services
 * - Limits resource consumption per service
 * - Prevents one failing service from affecting others
 * 
 * Benefits:
 * - Fault isolation between services
 * - Prevents resource exhaustion
 * - Improved system resilience
 * - Controlled resource allocation
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Bulkhead Pattern ===");
        System.out.println("Resource isolation for fault tolerance");
        System.out.println();
        
        BulkheadService bulkheadService = new BulkheadService();
        
        // Create separate bulkheads for different services
        bulkheadService.createBulkhead("PaymentService", 2);
        bulkheadService.createBulkhead("NotificationService", 3);
        bulkheadService.createBulkhead("ReportingService", 1);
        
        try {
            // Execute tasks in isolated bulkheads
            Future<String> payment1 = bulkheadService.executeTask("PaymentService", 
                new ServiceTask("Payment-1", 1000, false));
            
            Future<String> payment2 = bulkheadService.executeTask("PaymentService", 
                new ServiceTask("Payment-2", 2000, true)); // This will fail
            
            Future<String> notification = bulkheadService.executeTask("NotificationService", 
                new ServiceTask("Notification-1", 500, false));
            
            Future<String> reporting = bulkheadService.executeTask("ReportingService", 
                new ServiceTask("Reporting-1", 800, false));
            
            // Wait for results
            System.out.println("\n--- Results ---");
            
            try {
                System.out.println("Payment-1: " + payment1.get());
            } catch (Exception e) {
                System.out.println("Payment-1 failed: " + e.getMessage());
            }
            
            try {
                System.out.println("Payment-2: " + payment2.get());
            } catch (Exception e) {
                System.out.println("Payment-2 failed: " + e.getCause().getMessage());
            }
            
            try {
                System.out.println("Notification: " + notification.get());
            } catch (Exception e) {
                System.out.println("Notification failed: " + e.getMessage());
            }
            
            try {
                System.out.println("Reporting: " + reporting.get());
            } catch (Exception e) {
                System.out.println("Reporting failed: " + e.getMessage());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bulkheadService.shutdown();
        }
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Fault isolation between services");
        System.out.println("✅ Prevented resource exhaustion");
        System.out.println("✅ One service failure didn't affect others");
        System.out.println("✅ Controlled resource allocation");
    }
}