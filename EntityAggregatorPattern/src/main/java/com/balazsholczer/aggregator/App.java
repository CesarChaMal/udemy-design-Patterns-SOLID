package com.balazsholczer.aggregator;

import java.util.List;

/**
 * Entity Aggregator Pattern: aggregates multiple entities
 * 
 * Key Concepts:
 * - Combines related entities into meaningful aggregates
 * - Provides unified view of distributed data
 * - Handles complex entity relationships
 * - Optimizes data access and presentation
 * 
 * Benefits:
 * - Simplified data access for complex relationships
 * - Reduced number of service calls
 * - Consistent aggregate creation
 * - Performance optimization through batching
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Entity Aggregator Pattern ===");
        System.out.println("Aggregating multiple entities");
        System.out.println();
        
        EntityAggregator aggregator = new EntityAggregator();
        
        // Aggregate single customer orders
        CustomerOrderAggregate customerAggregate = aggregator.aggregateCustomerOrders("C1");
        System.out.println("Customer aggregate: " + customerAggregate);
        System.out.println("Orders: " + customerAggregate.getOrders());
        
        System.out.println();
        
        // Aggregate all customer orders
        List<CustomerOrderAggregate> allAggregates = aggregator.aggregateAllCustomerOrders();
        System.out.println("All customer aggregates:");
        allAggregates.forEach(System.out::println);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Unified view of related entities");
        System.out.println("✅ Reduced service calls through aggregation");
        System.out.println("✅ Complex relationship handling");
        System.out.println("✅ Performance optimization");
    }
}