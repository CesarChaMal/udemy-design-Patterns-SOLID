package com.balazsholczer.dbperservice;

import java.math.BigDecimal;

public class OrderDatabase extends Database {
    
    public OrderDatabase() {
        super("Order");
    }
    
    public void saveOrder(String orderId, String userId, BigDecimal amount) {
        Order order = new Order(orderId, userId, amount);
        save(orderId, order);
    }
    
    public Order findOrder(String orderId) {
        return (Order) find(orderId);
    }
    
    public static class Order {
        private final String id;
        private final String userId;
        private final BigDecimal amount;
        
        public Order(String id, String userId, BigDecimal amount) {
            this.id = id;
            this.userId = userId;
            this.amount = amount;
        }
        
        public String getId() { return id; }
        public String getUserId() { return userId; }
        public BigDecimal getAmount() { return amount; }
        
        @Override
        public String toString() {
            return "Order{id='" + id + "', userId='" + userId + "', amount=" + amount + "}";
        }
    }
}