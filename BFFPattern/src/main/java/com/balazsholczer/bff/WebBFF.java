package com.balazsholczer.bff;

import java.math.BigDecimal;
import java.util.List;

public class WebBFF {
    private final UserService userService;
    private final OrderService orderService;
    
    public WebBFF(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }
    
    public WebDashboard getDashboard(String userId) {
        System.out.println("WebBFF: Creating web dashboard for " + userId);
        
        // Get data from multiple services
        UserService.UserData user = userService.getUserById(userId);
        List<OrderService.OrderData> orders = orderService.getOrdersByUserId(userId);
        
        // Create web-optimized response with detailed information
        BigDecimal totalSpent = orders.stream()
            .map(o -> o.amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return new WebDashboard(
            user.name,
            user.email,
            user.tier,
            orders,
            totalSpent
        );
    }
    
    public static class WebDashboard {
        public final String name;
        public final String email;
        public final String tier;
        public final List<OrderService.OrderData> recentOrders;
        public final BigDecimal totalSpent;
        
        public WebDashboard(String name, String email, String tier, 
                           List<OrderService.OrderData> recentOrders, BigDecimal totalSpent) {
            this.name = name;
            this.email = email;
            this.tier = tier;
            this.recentOrders = recentOrders;
            this.totalSpent = totalSpent;
        }
        
        @Override
        public String toString() {
            return "WebDashboard{name='" + name + "', email='" + email + 
                   "', tier='" + tier + "', orders=" + recentOrders.size() + 
                   ", totalSpent=" + totalSpent + "}";
        }
    }
}