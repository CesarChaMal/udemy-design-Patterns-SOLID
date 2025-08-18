package com.balazsholczer.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUserRepository implements UserRepository {
    
    private final Map<String, User> database = new HashMap<>();
    
    @Override
    public void save(User user) {
        System.out.println("DatabaseUserRepository: Saving user to database - " + user.getId());
        database.put(user.getId(), user);
    }
    
    @Override
    public User findById(String id) {
        System.out.println("DatabaseUserRepository: Finding user by id - " + id);
        return database.get(id);
    }
    
    @Override
    public List<User> findAll() {
        System.out.println("DatabaseUserRepository: Finding all users");
        return new ArrayList<>(database.values());
    }
}