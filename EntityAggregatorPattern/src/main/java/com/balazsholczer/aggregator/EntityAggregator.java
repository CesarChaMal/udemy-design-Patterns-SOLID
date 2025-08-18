package com.balazsholczer.aggregator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityAggregator {
    
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Order> orders = new HashMap<>();
    
    public EntityAggregator() {
        // Initialize with sample data
        customers.put("C1", new Customer("C1", "John Doe", "john@example.com"));
        customers.put("C2", new Customer("C2", "Jane Smith", "jane@example.com"));
        
        orders.put("O1", new Order("O1", "C1", BigDecimal.valueOf(99.99)));
        orders.put("O2", new Order("O2", "C1", BigDecimal.valueOf(149.99)));
        orders.put("O3", new Order("O3", "C2", BigDecimal.valueOf(79.99)));
        
        System.out.println("EntityAggregator: Initialized with sample data");
    }
    
    public CustomerOrderAggregate aggregateCustomerOrders(String customerId) {
        System.out.println("EntityAggregator: Aggregating data for customer " + customerId);
        
        Customer customer = customers.get(customerId);
        if (customer == null) {
            return null;
        }
        
        List<Order> customerOrders = orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
        
        return new CustomerOrderAggregate(customer, customerOrders);
    }
    
    public List<CustomerOrderAggregate> aggregateAllCustomerOrders() {
        System.out.println("EntityAggregator: Aggregating all customer orders");
        
        return customers.keySet().stream()
                .map(this::aggregateCustomerOrders)
                .collect(Collectors.toList());
    }
}