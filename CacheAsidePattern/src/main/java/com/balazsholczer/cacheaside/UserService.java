package com.balazsholczer.cacheaside;

import java.util.Optional;

public class UserService {
    private final Database database;
    private final Cache<String, User> cache;
    
    public UserService(Database database, Cache<String, User> cache) {
        this.database = database;
        this.cache = cache;
    }
    
    public User getUser(String id) {
        System.out.println("UserService: Getting user " + id);
        
        // Try cache first (Cache-Aside pattern)
        Optional<User> cachedUser = cache.get(id);
        if (cachedUser.isPresent()) {
            return cachedUser.get();
        }
        
        // Cache miss - load from database
        User user = database.findById(id);
        if (user != null) {
            // Store in cache for future requests
            cache.put(id, user);
        }
        
        return user;
    }
    
    public void updateUser(User user) {
        System.out.println("UserService: Updating user " + user.getId());
        
        // Update database
        database.save(user);
        
        // Update cache (write-through)
        cache.put(user.getId(), user);
    }
    
    public void deleteUser(String id) {
        System.out.println("UserService: Deleting user " + id);
        
        // Delete from database
        database.delete(id);
        
        // Remove from cache
        cache.evict(id);
    }
}