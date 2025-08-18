package com.balazsholczer.stranglerfig;

public class LegacySystem {
    
    public String getUserData(String userId) {
        System.out.println("LegacySystem: Getting user data for " + userId);
        // Simulate legacy system processing
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Legacy user data for " + userId;
    }
    
    public String getOrderData(String orderId) {
        System.out.println("LegacySystem: Getting order data for " + orderId);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Legacy order data for " + orderId;
    }
}