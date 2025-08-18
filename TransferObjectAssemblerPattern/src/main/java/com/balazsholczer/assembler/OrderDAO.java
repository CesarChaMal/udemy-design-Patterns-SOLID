package com.balazsholczer.assembler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    
    private final Map<String, List<OrderTO>> ordersByCustomer = Map.of(
        "1", List.of(
            new OrderTO("ORD-001", "1", BigDecimal.valueOf(100.50), "COMPLETED"),
            new OrderTO("ORD-002", "1", BigDecimal.valueOf(75.25), "PENDING")
        ),
        "2", List.of(
            new OrderTO("ORD-003", "2", BigDecimal.valueOf(200.00), "SHIPPED")
        )
    );
    
    public List<OrderTO> findByCustomerId(String customerId) {
        System.out.println("OrderDAO: Finding orders for customer " + customerId);
        return ordersByCustomer.getOrDefault(customerId, List.of());
    }
}