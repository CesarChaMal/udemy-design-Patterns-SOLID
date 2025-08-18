package com.balazsholczer.cacheaside;

/**
 * Cache-Aside Pattern: caching strategy
 * 
 * Key Concepts:
 * - Application manages cache directly
 * - Cache miss triggers database load
 * - Cache is updated after database operations
 * - Provides performance optimization for read-heavy workloads
 * 
 * Benefits:
 * - Improved read performance
 * - Reduced database load
 * - Application controls caching logic
 * - Flexible cache management
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Cache-Aside Pattern ===");
        System.out.println("Caching strategy for performance");
        System.out.println();
        
        Database database = new Database();
        Cache<String, User> cache = new Cache<>("UserCache");
        UserService userService = new UserService(database, cache);
        
        // First access - cache miss, loads from database
        System.out.println("--- First Access ---");
        User user1 = userService.getUser("1");
        System.out.println("Retrieved: " + user1);
        
        System.out.println("\n--- Second Access (same user) ---");
        // Second access - cache hit, no database call
        User user1Again = userService.getUser("1");
        System.out.println("Retrieved: " + user1Again);
        
        System.out.println("\n--- Access Different User ---");
        // Different user - cache miss again
        User user2 = userService.getUser("2");
        System.out.println("Retrieved: " + user2);
        
        System.out.println("\n--- Update User ---");
        // Update user - updates both database and cache
        User updatedUser = new User("1", "John Updated", "john.updated@example.com");
        userService.updateUser(updatedUser);
        
        System.out.println("\n--- Access Updated User ---");
        // Access updated user - cache hit with new data
        User user1Updated = userService.getUser("1");
        System.out.println("Retrieved: " + user1Updated);
        
        System.out.println("\n--- Delete User ---");
        // Delete user - removes from both database and cache
        userService.deleteUser("2");
        
        System.out.println("\nCache size: " + cache.size());
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Improved read performance through caching");
        System.out.println("✅ Reduced database load on repeated access");
        System.out.println("✅ Application-controlled cache management");
        System.out.println("✅ Consistent data through cache invalidation");
    }
}