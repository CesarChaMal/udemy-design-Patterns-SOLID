package com.balazsholczer.dbperservice;

import java.math.BigDecimal;

public class OrderService {
    private final OrderDatabase database;
    
    public OrderService() {
        this.database = new OrderDatabase();
    }
    
    public void createOrder(String orderId, String userId, BigDecimal amount) {
        System.out.println("OrderService: Creating order " + orderId);
        database.saveOrder(orderId, userId, amount);
    }
    
    public OrderDatabase.Order getOrder(String orderId) {
        System.out.println("OrderService: Getting order " + orderId);
        return database.findOrder(orderId);
    }
    
    public void deleteOrder(String orderId) {
        System.out.println("OrderService: Deleting order " + orderId);
        database.delete(orderId);
    }
}