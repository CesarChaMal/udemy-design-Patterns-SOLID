package com.balazsholczer.daofactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLUserDAO implements UserDAO {
    
    private final Map<String, User> database = new HashMap<>();
    
    @Override
    public void save(User user) {
        System.out.println("MySQLUserDAO: Saving user to MySQL - " + user.getId());
        database.put(user.getId(), user);
    }
    
    @Override
    public User findById(String id) {
        System.out.println("MySQLUserDAO: Finding user in MySQL - " + id);
        return database.get(id);
    }
    
    @Override
    public List<User> findAll() {
        System.out.println("MySQLUserDAO: Finding all users in MySQL");
        return new ArrayList<>(database.values());
    }
    
    @Override
    public void delete(String id) {
        System.out.println("MySQLUserDAO: Deleting user from MySQL - " + id);
        database.remove(id);
    }
}