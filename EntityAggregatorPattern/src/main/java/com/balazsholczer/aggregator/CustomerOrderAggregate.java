package com.balazsholczer.aggregator;

import java.math.BigDecimal;
import java.util.List;

public class CustomerOrderAggregate {
    private Customer customer;
    private List<Order> orders;
    private BigDecimal totalSpent;
    
    public CustomerOrderAggregate(Customer customer, List<Order> orders) {
        this.customer = customer;
        this.orders = orders;
        this.totalSpent = calculateTotalSpent();
    }
    
    private BigDecimal calculateTotalSpent() {
        return orders.stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public Customer getCustomer() { return customer; }
    public List<Order> getOrders() { return orders; }
    public BigDecimal getTotalSpent() { return totalSpent; }
    public int getOrderCount() { return orders.size(); }
    
    @Override
    public String toString() {
        return "CustomerOrderAggregate{customer=" + customer + 
               ", orderCount=" + orders.size() + ", totalSpent=" + totalSpent + "}";
    }
}