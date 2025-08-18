package com.balazsholczer.mediator;

import java.util.concurrent.TimeUnit;

public class ModernMediatorDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Traditional Mediator Pattern ===");
        
        ConcreteMediator mediator = new ConcreteMediator();
        
        ConcreteColleague alice = new ConcreteColleague(mediator, "Alice");
        ConcreteColleague bob = new ConcreteColleague(mediator, "Bob");
        ConcreteColleague charlie = new ConcreteColleague(mediator, "Charlie");
        
        mediator.addColleague(alice);
        mediator.addColleague(bob);
        mediator.addColleague(charlie);
        
        alice.send("Hello everyone!");
        bob.send("Hi Alice!");
        charlie.send("Good morning all!");
        
        System.out.println("\n=== Event Mediator Pattern ===");
        
        EventMediator eventMediator = new EventMediator();
        
        // Subscribe to events
        eventMediator.subscribe("user.login", "SecurityService", 
            event -> System.out.println("SecurityService: User " + event.data() + " logged in"));
        
        eventMediator.subscribe("user.login", "AuditService", 
            event -> System.out.println("AuditService: Logging user " + event.data() + " login"));
        
        eventMediator.subscribe("order.created", "InventoryService", 
            event -> System.out.println("InventoryService: Processing order " + event.data()));
        
        eventMediator.subscribe("order.created", "EmailService", 
            event -> System.out.println("EmailService: Sending confirmation for order " + event.data()));
        
        // Publish events
        eventMediator.publish("user.login", "john_doe", "AuthController");
        eventMediator.publish("order.created", "ORDER-123", "OrderController");
        
        System.out.println("\n=== Functional Mediator Pattern ===");
        
        FunctionalMediator funcMediator = new FunctionalMediator();
        
        // Create participants with custom handlers
        var alice2 = FunctionalMediator.createParticipant("Alice", 
            msg -> System.out.println("Alice got: '" + msg.content() + "' from " + msg.from()));
        
        var bob2 = FunctionalMediator.createParticipant("Bob");
        
        var charlie2 = FunctionalMediator.createParticipant("Charlie", 
            msg -> System.out.println("Charlie received: '" + msg.content() + "' (sender: " + msg.from() + ")"));
        
        // Register participants
        funcMediator.register(alice2);
        funcMediator.register(bob2);
        funcMediator.register(charlie2);
        
        // Send messages
        alice2.send("Hello Bob!", "Bob", funcMediator);
        bob2.send("Hi everyone!", "ALL", funcMediator);
        charlie2.send("Private message to Alice", "Alice", funcMediator);
        
        System.out.println("\n=== Stream Mediator Pattern ===");
        
        StreamMediator streamMediator = new StreamMediator();
        
        // Subscribe with different filters
        streamMediator.subscribe("UserService", StreamMediator.typeFilter("user.event"));
        streamMediator.subscribe("OrderService", StreamMediator.typeFilter("order.event"));
        streamMediator.subscribe("AuditService", StreamMediator.excludeSender("AuditService"));
        streamMediator.subscribe("LoggingService", msg -> true); // All messages
        
        // Publish messages
        streamMediator.publish("user.event", "User registered", "UserController");
        streamMediator.publish("order.event", "Order placed", "OrderController");
        streamMediator.publish("system.event", "System startup", "SystemService");
        
        // Wait for async processing
        TimeUnit.MILLISECONDS.sleep(100);
        
        System.out.println("\n=== Advanced Features ===");
        
        System.out.println("Event Mediator - Subscription count:");
        System.out.println("user.login subscribers: " + eventMediator.getSubscriberCount("user.login"));
        System.out.println("order.created subscribers: " + eventMediator.getSubscriberCount("order.created"));
        
        System.out.println("Functional Mediator - Error handling:");
        funcMediator.route(new FunctionalMediator.Message("Test message", "Alice", "NonExistentUser"));
        
        System.out.println("Stream Mediator - Active subscribers:");
        streamMediator.getActiveSubscribers().forEach(sub -> System.out.println("  - " + sub));
        
        System.out.println("Event Mediator - Unsubscribe:");
        eventMediator.unsubscribe("user.login", "SecurityService");
        eventMediator.publish("user.login", "jane_doe", "AuthController");
        
        // Cleanup
        streamMediator.close();
        
        System.out.println("\n=== Pattern Comparison ===");
        System.out.println("Traditional: Direct colleague communication through mediator");
        System.out.println("Event: Publish-subscribe with event types and handlers");
        System.out.println("Functional: Lambda-based message routing with custom logic");
        System.out.println("Stream: Reactive streams with filtering and async processing");
    }
}