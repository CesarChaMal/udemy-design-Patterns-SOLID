package com.balazsholczer.daofactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgreSQLUserDAO implements UserDAO {
    
    private final Map<String, User> database = new HashMap<>();
    
    @Override
    public void save(User user) {
        System.out.println("PostgreSQLUserDAO: Saving user to PostgreSQL - " + user.getId());
        database.put(user.getId(), user);
    }
    
    @Override
    public User findById(String id) {
        System.out.println("PostgreSQLUserDAO: Finding user in PostgreSQL - " + id);
        return database.get(id);
    }
    
    @Override
    public List<User> findAll() {
        System.out.println("PostgreSQLUserDAO: Finding all users in PostgreSQL");
        return new ArrayList<>(database.values());
    }
    
    @Override
    public void delete(String id) {
        System.out.println("PostgreSQLUserDAO: Deleting user from PostgreSQL - " + id);
        database.remove(id);
    }
}