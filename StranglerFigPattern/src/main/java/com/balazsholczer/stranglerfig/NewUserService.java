package com.balazsholczer.stranglerfig;

public class NewUserService {
    
    public String getUserData(String userId) {
        System.out.println("NewUserService: Getting user data for " + userId);
        // Modern, fast implementation
        return "Modern user data for " + userId + " (fast, scalable)";
    }
}