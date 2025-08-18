package com.balazsholczer.serviceactivator;

import java.util.concurrent.CompletableFuture;

/**
 * Service Activator Pattern: asynchronous service invocation
 * 
 * Key Concepts:
 * - Enables asynchronous invocation of business services
 * - Decouples message reception from service processing
 * - Provides thread pool management for concurrent processing
 * - Supports message-driven architecture and event processing
 * 
 * Structure:
 * - ServiceActivator: receives messages and activates appropriate services
 * - BusinessService: processes messages asynchronously
 * - Message: contains data and metadata for service processing
 * - MessageQueue: manages message queuing and delivery
 * 
 * Benefits:
 * - Asynchronous processing improves system responsiveness
 * - Decouples message producers from service processors
 * - Enables scalable concurrent processing
 * - Supports message-driven and event-driven architectures
 * 
 * Use Cases:
 * - Message-driven beans in J2EE applications
 * - Event processing systems
 * - Asynchronous service invocation
 * - Background task processing
 */

public class App {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Service Activator Pattern ===");
        System.out.println("Asynchronous service invocation");
        System.out.println();
        
        System.out.println("=== Traditional Service Activator ===");
        
        ServiceActivator serviceActivator = new ServiceActivator();
        MessageQueue messageQueue = new MessageQueue(serviceActivator);
        
        // Start message processing
        messageQueue.startProcessing();
        
        // Send messages to queue
        Message orderMessage1 = new Message("MSG-001", "ORDER", "Order for 2 laptops");
        Message emailMessage1 = new Message("MSG-002", "EMAIL", "Welcome email to john@example.com");
        Message orderMessage2 = new Message("MSG-003", "ORDER", "Order for 1 phone");
        Message emailMessage2 = new Message("MSG-004", "EMAIL", "Order confirmation to jane@example.com");
        
        messageQueue.sendMessage(orderMessage1);
        messageQueue.sendMessage(emailMessage1);
        messageQueue.sendMessage(orderMessage2);
        messageQueue.sendMessage(emailMessage2);
        
        System.out.println("Messages queued. Queue size: " + messageQueue.getQueueSize());
        
        // Wait for processing
        Thread.sleep(3000);
        
        System.out.println("\n=== Functional Service Activator ===");
        
        FunctionalServiceActivator funcActivator = new FunctionalServiceActivator();
        
        // Create messages for functional processing
        Message funcOrder = new Message("FUNC-001", "ORDER", "Functional order processing");
        Message funcEmail = new Message("FUNC-002", "EMAIL", "Functional email sending");
        Message funcNotification = new Message("FUNC-003", "NOTIFICATION", "Functional notification");
        
        // Process messages asynchronously with CompletableFuture
        CompletableFuture<Void> orderFuture = funcActivator.activateService(funcOrder);
        CompletableFuture<Void> emailFuture = funcActivator.activateService(funcEmail);
        CompletableFuture<Void> notificationFuture = funcActivator.activateService(funcNotification);
        
        // Wait for completion
        CompletableFuture.allOf(orderFuture, emailFuture, notificationFuture).join();
        
        System.out.println("\n=== Batch Processing ===");
        
        // Process multiple messages in batch
        Message batchMsg1 = new Message("BATCH-001", "ORDER", "Batch order 1");
        Message batchMsg2 = new Message("BATCH-002", "EMAIL", "Batch email 1");
        Message batchMsg3 = new Message("BATCH-003", "NOTIFICATION", "Batch notification 1");
        
        CompletableFuture<Void> batchFuture = funcActivator.activateMultipleServices(batchMsg1, batchMsg2, batchMsg3);
        batchFuture.join();
        
        System.out.println("Batch processing completed");
        
        System.out.println("\n=== Custom Service Registration ===");
        
        // Register custom service
        serviceActivator.registerService("PAYMENT", message -> {
            System.out.println("PaymentService: Processing payment - " + message.getPayload());
            try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("PaymentService: Payment processed - " + message.getId());
        });
        
        Message paymentMessage = new Message("PAY-001", "PAYMENT", "Payment for $299.99");
        messageQueue.sendMessage(paymentMessage);
        
        // Wait for custom service processing
        Thread.sleep(2000);
        
        System.out.println("\n=== Synchronous vs Asynchronous Comparison ===");
        
        long startTime = System.currentTimeMillis();
        
        // Simulate synchronous processing
        System.out.println("Synchronous processing:");
        OrderService syncOrderService = new OrderService();
        EmailService syncEmailService = new EmailService();
        
        Message syncMsg1 = new Message("SYNC-001", "ORDER", "Sync order");
        Message syncMsg2 = new Message("SYNC-002", "EMAIL", "Sync email");
        
        syncOrderService.processMessage(syncMsg1);
        syncEmailService.processMessage(syncMsg2);
        
        long syncTime = System.currentTimeMillis() - startTime;
        System.out.println("Synchronous processing time: " + syncTime + "ms");
        
        // Simulate asynchronous processing
        startTime = System.currentTimeMillis();
        System.out.println("\nAsynchronous processing:");
        
        Message asyncMsg1 = new Message("ASYNC-001", "ORDER", "Async order");
        Message asyncMsg2 = new Message("ASYNC-002", "EMAIL", "Async email");
        
        CompletableFuture<Void> async1 = funcActivator.activateService(asyncMsg1);
        CompletableFuture<Void> async2 = funcActivator.activateService(asyncMsg2);
        
        CompletableFuture.allOf(async1, async2).join();
        
        long asyncTime = System.currentTimeMillis() - startTime;
        System.out.println("Asynchronous processing time: " + asyncTime + "ms");
        
        // Cleanup
        messageQueue.stopProcessing();
        serviceActivator.shutdown();
        funcActivator.shutdown();
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Asynchronous processing improves responsiveness");
        System.out.println("✅ Concurrent processing reduces total execution time");
        System.out.println("✅ Decoupled message producers from service processors");
        System.out.println("✅ Scalable thread pool management");
        System.out.println("✅ Support for message-driven architecture");
    }
}