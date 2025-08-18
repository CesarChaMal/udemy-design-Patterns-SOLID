package com.balazsholczer.bff;

import java.util.List;

public class MobileBFF {
    private final UserService userService;
    private final OrderService orderService;
    
    public MobileBFF(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }
    
    public MobileUserProfile getUserProfile(String userId) {
        System.out.println("MobileBFF: Creating mobile user profile for " + userId);
        
        // Get data from multiple services
        UserService.UserData user = userService.getUserById(userId);
        List<OrderService.OrderData> orders = orderService.getOrdersByUserId(userId);
        
        // Create mobile-optimized response
        return new MobileUserProfile(
            user.name,
            user.tier,
            orders.size(),
            orders.stream().anyMatch(o -> "Processing".equals(o.status))
        );
    }
    
    public static class MobileUserProfile {
        public final String name;
        public final String tier;
        public final int totalOrders;
        public final boolean hasActiveOrders;
        
        public MobileUserProfile(String name, String tier, int totalOrders, boolean hasActiveOrders) {
            this.name = name;
            this.tier = tier;
            this.totalOrders = totalOrders;
            this.hasActiveOrders = hasActiveOrders;
        }
        
        @Override
        public String toString() {
            return "MobileUserProfile{name='" + name + "', tier='" + tier + 
                   "', totalOrders=" + totalOrders + ", hasActiveOrders=" + hasActiveOrders + "}";
        }
    }
}