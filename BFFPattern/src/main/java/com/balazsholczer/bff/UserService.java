package com.balazsholczer.bff;

public class UserService {
    
    public UserData getUserById(String userId) {
        System.out.println("UserService: Getting user " + userId);
        return new UserData(userId, "John Doe", "john@example.com", "Premium");
    }
    
    public static class UserData {
        public final String id;
        public final String name;
        public final String email;
        public final String tier;
        
        public UserData(String id, String name, String email, String tier) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.tier = tier;
        }
    }
}