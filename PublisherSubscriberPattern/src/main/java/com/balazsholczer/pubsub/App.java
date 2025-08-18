package com.balazsholczer.pubsub;

/**
 * Publisher-Subscriber Pattern: async messaging
 * 
 * Key Concepts:
 * - Decouples message producers from consumers
 * - Topic-based message routing
 * - Asynchronous message delivery
 * - Many-to-many communication pattern
 * 
 * Benefits:
 * - Loose coupling between components
 * - Scalable message distribution
 * - Dynamic subscription management
 * - Event-driven architecture support
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Publisher-Subscriber Pattern ===");
        System.out.println("Async messaging with topic-based routing");
        System.out.println();
        
        MessageBroker broker = new MessageBroker();
        
        // Create subscribers
        Subscriber emailSub1 = new EmailSubscriber("email-service-1");
        Subscriber emailSub2 = new EmailSubscriber("email-service-2");
        Subscriber logSub = new Subscriber() {
            @Override
            public void onMessage(Message message) {
                System.out.println("LogSubscriber: Logging " + message);
            }
            
            @Override
            public String getSubscriberId() {
                return "log-service";
            }
        };
        
        // Subscribe to topics
        broker.subscribe("user.created", emailSub1);
        broker.subscribe("user.created", logSub);
        broker.subscribe("order.placed", emailSub2);
        broker.subscribe("order.placed", logSub);
        
        // Publish messages
        broker.publish(new Message("user.created", "User John Doe created"));
        broker.publish(new Message("order.placed", "Order #123 placed"));
        broker.publish(new Message("payment.processed", "Payment $99.99 processed"));
        
        System.out.println("\n--- Unsubscribe and republish ---");
        broker.unsubscribe("user.created", emailSub1);
        broker.publish(new Message("user.created", "User Jane Smith created"));
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Loose coupling between publishers and subscribers");
        System.out.println("✅ Topic-based message routing");
        System.out.println("✅ Dynamic subscription management");
        System.out.println("✅ Scalable message distribution");
    }
}