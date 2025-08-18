package com.balazsholczer.mediator;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Mediator Pattern
 * Tests Traditional, Event-driven, Functional, and Stream approaches
 */
class MediatorTest {
    
    @Test
    void testTraditionalMediator() {
        ConcreteMediator mediator = new ConcreteMediator();
        
        ConcreteColleague alice = new ConcreteColleague(mediator, "Alice");
        ConcreteColleague bob = new ConcreteColleague(mediator, "Bob");
        ConcreteColleague charlie = new ConcreteColleague(mediator, "Charlie");
        
        mediator.addColleague(alice);
        mediator.addColleague(bob);
        mediator.addColleague(charlie);
        
        assertDoesNotThrow(() -> alice.send("Hello everyone!"));
        assertDoesNotThrow(() -> bob.send("Hi Alice!"));
        assertDoesNotThrow(() -> charlie.send("Good morning!"));
        
        assertEquals("Alice", alice.getName());
        assertEquals("Bob", bob.getName());
        assertEquals("Charlie", charlie.getName());
    }
    
    @Test
    void testEventMediator() {
        EventMediator mediator = new EventMediator();
        AtomicInteger[] counters = {new AtomicInteger(0), new AtomicInteger(0), new AtomicInteger(0)};
        
        // Subscribe to events with correct signature: eventType, subscriberId, handler
        mediator.subscribe("chat", "user1", event -> counters[0].incrementAndGet());
        mediator.subscribe("chat", "user2", event -> counters[1].incrementAndGet());
        mediator.subscribe("notification", "user3", event -> counters[2].incrementAndGet());
        
        // Publish events with correct signature: eventType, data, source
        mediator.publish("chat", "Hello World", "sender1");
        mediator.publish("notification", "New message", "system");
        
        // Verify event delivery
        assertEquals(1, counters[0].get());
        assertEquals(1, counters[1].get());
        assertEquals(1, counters[2].get());
        
        assertEquals(2, mediator.getSubscriberCount("chat"));
        assertEquals(1, mediator.getSubscriberCount("notification"));
    }
    
    @Test
    void testEventMediatorWithRecord() {
        EventMediator mediator = new EventMediator();
        AtomicInteger counter = new AtomicInteger(0);
        
        mediator.subscribe("test", "subscriber1", event -> {
            assertNotNull(event.type());
            assertNotNull(event.data());
            assertNotNull(event.source());
            counter.incrementAndGet();
        });
        
        EventMediator.Event event = new EventMediator.Event("test", "data", "source");
        mediator.publish(event);
        
        assertEquals(1, counter.get());
    }
    
    @Test
    void testEventMediatorUnsubscribe() {
        EventMediator mediator = new EventMediator();
        AtomicInteger counter = new AtomicInteger(0);
        
        mediator.subscribe("test", "user1", event -> counter.incrementAndGet());
        mediator.publish("test", "message", "sender");
        assertEquals(1, counter.get());
        
        mediator.unsubscribe("test", "user1");
        mediator.publish("test", "message2", "sender");
        assertEquals(1, counter.get()); // Should not increment
        
        assertEquals(0, mediator.getSubscriberCount("test"));
    }
    
    @Test
    void testFunctionalMediator() {
        FunctionalMediator mediator = new FunctionalMediator();
        AtomicInteger counter = new AtomicInteger(0);
        
        FunctionalMediator.Participant participant = FunctionalMediator.createParticipant(
            "user1", 
            msg -> counter.incrementAndGet()
        );
        
        mediator.register(participant);
        mediator.route(new FunctionalMediator.Message("Hello", "system", "user1"));
        
        assertEquals(1, counter.get());
    }
    
    @Test
    void testFunctionalMediatorBroadcast() {
        FunctionalMediator mediator = new FunctionalMediator();
        AtomicInteger counter1 = new AtomicInteger(0);
        AtomicInteger counter2 = new AtomicInteger(0);
        
        FunctionalMediator.Participant p1 = FunctionalMediator.createParticipant(
            "user1", msg -> counter1.incrementAndGet());
        FunctionalMediator.Participant p2 = FunctionalMediator.createParticipant(
            "user2", msg -> counter2.incrementAndGet());
        
        mediator.register(p1);
        mediator.register(p2);
        
        // Broadcast message
        mediator.route(new FunctionalMediator.Message("Broadcast", "system", "ALL"));
        
        assertEquals(1, counter1.get());
        assertEquals(1, counter2.get());
    }
    
    @Test
    void testStreamMediator() {
        StreamMediator mediator = new StreamMediator();
        
        // Subscribe with type filter
        mediator.subscribe("subscriber1", StreamMediator.typeFilter("important"));
        
        mediator.publish("important", "data1", "sender1");
        mediator.publish("normal", "data2", "sender2");
        
        // Give some time for async processing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(mediator.getActiveSubscribers().contains("subscriber1"));
        mediator.close();
    }
    
    @Test
    void testMediatorPatternBehavior() {
        ConcreteMediator mediator = new ConcreteMediator();
        ConcreteColleague alice = new ConcreteColleague(mediator, "Alice");
        ConcreteColleague bob = new ConcreteColleague(mediator, "Bob");
        
        mediator.addColleague(alice);
        mediator.addColleague(bob);
        
        // Test that colleagues can communicate through mediator
        assertDoesNotThrow(() -> {
            alice.send("Message from Alice");
            bob.send("Reply from Bob");
        });
        
        // Test colleague properties
        assertEquals("Alice", alice.getName());
        assertEquals("Bob", bob.getName());
    }
    
    @Test
    void testEventMediatorErrorHandling() {
        EventMediator mediator = new EventMediator();
        
        // Subscribe with handler that throws exception
        mediator.subscribe("error-test", "subscriber1", event -> {
            throw new RuntimeException("Test exception");
        });
        
        // Should not throw exception, should handle gracefully
        assertDoesNotThrow(() -> mediator.publish("error-test", "data", "source"));
    }
    
    @Test
    void testEquivalence() {
        // All approaches should facilitate communication
        
        // Traditional - colleague communication
        ConcreteMediator traditional = new ConcreteMediator();
        ConcreteColleague colleague = new ConcreteColleague(traditional, "Test");
        traditional.addColleague(colleague);
        assertDoesNotThrow(() -> colleague.send("Traditional message"));
        
        // Event - publish/subscribe
        EventMediator event = new EventMediator();
        AtomicInteger eventCounter = new AtomicInteger(0);
        event.subscribe("test", "subscriber", e -> eventCounter.incrementAndGet());
        event.publish("test", "Event message", "source");
        assertEquals(1, eventCounter.get());
        
        // Functional - handler-based
        FunctionalMediator functional = new FunctionalMediator();
        AtomicInteger functionalCounter = new AtomicInteger(0);
        FunctionalMediator.Participant participant = FunctionalMediator.createParticipant(
            "test", msg -> functionalCounter.incrementAndGet());
        functional.register(participant);
        functional.route(new FunctionalMediator.Message("Functional message", "system", "test"));
        assertEquals(1, functionalCounter.get());
        
        // Stream - reactive
        StreamMediator stream = new StreamMediator();
        stream.subscribe("subscriber", msg -> true);
        stream.publish("test", "Stream message", "sender");
        
        // Give time for async processing
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(stream.getActiveSubscribers().contains("subscriber"));
        stream.close();
    }
}