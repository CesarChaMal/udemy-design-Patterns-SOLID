package com.balazsholczer.bff;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class OrderService {
    
    public List<OrderData> getOrdersByUserId(String userId) {
        System.out.println("OrderService: Getting orders for user " + userId);
        return Arrays.asList(
            new OrderData("O001", userId, BigDecimal.valueOf(299.99), "Shipped"),
            new OrderData("O002", userId, BigDecimal.valueOf(149.99), "Processing")
        );
    }
    
    public static class OrderData {
        public final String id;
        public final String userId;
        public final BigDecimal amount;
        public final String status;
        
        public OrderData(String id, String userId, BigDecimal amount, String status) {
            this.id = id;
            this.userId = userId;
            this.amount = amount;
            this.status = status;
        }
    }
}