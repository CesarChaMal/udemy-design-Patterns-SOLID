package com.balazsholczer.cacheaside;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private final Map<String, User> users = new HashMap<>();
    
    public Database() {
        // Initialize with sample data
        users.put("1", new User("1", "John Doe", "john@example.com"));
        users.put("2", new User("2", "Jane Smith", "jane@example.com"));
        users.put("3", new User("3", "Bob Johnson", "bob@example.com"));
    }
    
    public User findById(String id) {
        System.out.println("Database: Querying user " + id + " (expensive operation)");
        
        // Simulate database delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return users.get(id);
    }
    
    public void save(User user) {
        System.out.println("Database: Saving user " + user.getId());
        users.put(user.getId(), user);
        
        // Simulate database delay
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void delete(String id) {
        System.out.println("Database: Deleting user " + id);
        users.remove(id);
    }
}