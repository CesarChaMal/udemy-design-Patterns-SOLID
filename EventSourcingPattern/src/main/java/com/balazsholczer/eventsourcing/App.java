package com.balazsholczer.eventsourcing;

import java.math.BigDecimal;
import java.util.List;

/**
 * Event Sourcing Pattern: event-based state management
 * 
 * Key Concepts:
 * - Stores events instead of current state
 * - Rebuilds aggregate state from event history
 * - Provides complete audit trail
 * - Enables temporal queries and replay
 * 
 * Benefits:
 * - Complete audit trail
 * - Temporal queries capability
 * - Event replay and debugging
 * - Natural fit for event-driven architecture
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Event Sourcing Pattern ===");
        System.out.println("Event-based state management");
        System.out.println();
        
        EventStore eventStore = new EventStore();
        String accountId = "ACC-001";
        
        // Create account
        AccountCreatedEvent created = new AccountCreatedEvent(accountId, "John Doe", BigDecimal.valueOf(1000));
        eventStore.saveEvent(created);
        
        // Deposit money
        MoneyDepositedEvent deposit1 = new MoneyDepositedEvent(accountId, BigDecimal.valueOf(500));
        eventStore.saveEvent(deposit1);
        
        MoneyDepositedEvent deposit2 = new MoneyDepositedEvent(accountId, BigDecimal.valueOf(250));
        eventStore.saveEvent(deposit2);
        
        // Rebuild account from events
        List<Event> events = eventStore.getEvents(accountId);
        BankAccount account = BankAccount.fromEvents(accountId, events);
        
        System.out.println("Rebuilt account: " + account);
        
        // Show event history
        System.out.println("\nEvent History:");
        events.forEach(event -> System.out.println("- " + event));
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Complete audit trail");
        System.out.println("✅ State rebuilding from events");
        System.out.println("✅ Event history preservation");
        System.out.println("✅ Temporal query capability");
    }
}