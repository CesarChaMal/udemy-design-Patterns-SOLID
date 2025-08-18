package com.balazsholczer.appcontroller;

import java.util.Map;

public class OrderCommand implements Command {
    
    @Override
    public String execute(Map<String, Object> context) {
        System.out.println("OrderCommand: Processing order");
        
        Integer quantity = (Integer) context.get("quantity");
        String product = (String) context.get("product");
        
        // Simulate order processing
        if (quantity != null && quantity > 0 && product != null) {
            context.put("orderId", "ORD-" + System.currentTimeMillis());
            context.put("message", "Order placed successfully");
            return "order_success";
        } else {
            context.put("error", "Invalid order details");
            return "order_failure";
        }
    }
}