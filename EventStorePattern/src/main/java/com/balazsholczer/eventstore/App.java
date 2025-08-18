package com.balazsholczer.eventstore;

import java.math.BigDecimal;
import java.util.List;

/**
 * Event Store Pattern: event persistence and replay
 * 
 * Key Concepts:
 * - Persists events in append-only log
 * - Provides event replay capabilities
 * - Supports temporal queries and projections
 * - Enables event-driven architecture patterns
 * 
 * Benefits:
 * - Complete audit trail of all changes
 * - Event replay for debugging and analysis
 * - Temporal queries and time-travel debugging
 * - Foundation for event sourcing and CQRS
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Event Store Pattern ===");
        System.out.println("Event persistence and replay");
        System.out.println();
        
        EventStore eventStore = new EventStore();
        String accountId = "ACC-001";
        
        // Store events in the event store
        System.out.println("--- Storing Events ---");
        eventStore.appendEvent(accountId, new AccountEvents.AccountOpened(accountId, "John Doe", BigDecimal.valueOf(1000)));
        eventStore.appendEvent(accountId, new AccountEvents.MoneyDeposited(accountId, BigDecimal.valueOf(500)));
        eventStore.appendEvent(accountId, new AccountEvents.MoneyWithdrawn(accountId, BigDecimal.valueOf(200)));
        eventStore.appendEvent(accountId, new AccountEvents.MoneyDeposited(accountId, BigDecimal.valueOf(300)));
        
        // Replay events from stream
        System.out.println("\n--- Event Stream Replay ---");
        List<EventStore.StoredEvent> accountEvents = eventStore.getEventStream(accountId);
        System.out.println("Account " + accountId + " event history:");
        accountEvents.forEach(event -> System.out.println("- " + event.getEventData()));
        
        // Partial replay from specific version
        System.out.println("\n--- Partial Replay (from version 2) ---");
        List<EventStore.StoredEvent> recentEvents = eventStore.getEventStream(accountId, 2);
        System.out.println("Recent events:");
        recentEvents.forEach(event -> System.out.println("- " + event.getEventData()));
        
        // Query by event type
        System.out.println("\n--- Query by Event Type ---");
        List<EventStore.StoredEvent> deposits = eventStore.getEventsByType("MoneyDeposited");
        System.out.println("All deposit events:");
        deposits.forEach(event -> System.out.println("- " + event.getEventData()));
        
        // Global event log
        System.out.println("\n--- Global Event Log ---");
        List<EventStore.StoredEvent> allEvents = eventStore.getAllEvents();
        System.out.println("Total events in store: " + allEvents.size());
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Complete audit trail of all changes");
        System.out.println("✅ Event replay capabilities");
        System.out.println("✅ Temporal queries and projections");
        System.out.println("✅ Foundation for event-driven architecture");
    }
}