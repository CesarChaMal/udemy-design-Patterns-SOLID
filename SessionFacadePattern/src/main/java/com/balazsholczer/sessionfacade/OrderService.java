package com.balazsholczer.sessionfacade;

import java.math.BigDecimal;
import java.util.List;

public class OrderService {
    
    public Order createOrder(String customerId, List<String> items) {
        System.out.println("OrderService: Creating order for customer " + customerId);
        String orderId = "ORD-" + System.currentTimeMillis();
        BigDecimal total = BigDecimal.valueOf(items.size() * 100); // Simple calculation
        return new Order(orderId, customerId, items, total);
    }
    
    public void updateOrder(Order order) {
        System.out.println("OrderService: Updating order " + order.getOrderId());
    }
    
    public Order getOrder(String orderId) {
        System.out.println("OrderService: Retrieving order " + orderId);
        return new Order(orderId, "CUST-123", List.of("Item1", "Item2"), BigDecimal.valueOf(200));
    }
}