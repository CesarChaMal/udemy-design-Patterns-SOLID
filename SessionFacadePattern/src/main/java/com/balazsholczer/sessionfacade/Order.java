package com.balazsholczer.sessionfacade;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private List<String> items;
    private BigDecimal total;
    
    public Order(String orderId, String customerId, List<String> items, BigDecimal total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.total = total;
    }
    
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<String> getItems() { return items; }
    public BigDecimal getTotal() { return total; }
    
    @Override
    public String toString() {
        return "Order{id='" + orderId + "', customerId='" + customerId + "', items=" + items + ", total=" + total + "}";
    }
}