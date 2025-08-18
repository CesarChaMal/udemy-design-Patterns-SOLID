package com.balazsholczer.dtofactory;

import java.math.BigDecimal;

public class OrderDTO {
    private String orderId;
    private String customerId;
    private BigDecimal total;
    
    public OrderDTO(String orderId, String customerId, BigDecimal total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
    }
    
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getTotal() { return total; }
    
    @Override
    public String toString() {
        return "OrderDTO{orderId='" + orderId + "', customerId='" + customerId + "', total=" + total + "}";
    }
}