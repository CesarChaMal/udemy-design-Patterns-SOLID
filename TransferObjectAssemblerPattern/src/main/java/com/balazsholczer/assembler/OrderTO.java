package com.balazsholczer.assembler;

import java.math.BigDecimal;

public class OrderTO {
    private String orderId;
    private String customerId;
    private BigDecimal total;
    private String status;
    
    public OrderTO() {}
    
    public OrderTO(String orderId, String customerId, BigDecimal total, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
        this.status = status;
    }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "OrderTO{id='" + orderId + "', total=" + total + ", status='" + status + "'}";
    }
}