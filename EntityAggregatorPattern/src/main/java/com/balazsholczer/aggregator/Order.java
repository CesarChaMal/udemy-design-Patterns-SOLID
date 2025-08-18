package com.balazsholczer.aggregator;

import java.math.BigDecimal;

public class Order {
    private String id;
    private String customerId;
    private BigDecimal total;
    
    public Order(String id, String customerId, BigDecimal total) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
    }
    
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getTotal() { return total; }
    
    @Override
    public String toString() {
        return "Order{id='" + id + "', customerId='" + customerId + "', total=" + total + "}";
    }
}