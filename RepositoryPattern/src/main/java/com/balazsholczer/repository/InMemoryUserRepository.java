package com.balazsholczer.repository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    
    private final Map<String, User> users = new HashMap<>();
    
    @Override
    public void save(User user) {
        users.put(user.getId(), user);
        System.out.println("InMemoryUserRepository: Saved user " + user.getId());
    }
    
    @Override
    public Optional<User> findById(String id) {
        System.out.println("InMemoryUserRepository: Finding user by id " + id);
        return Optional.ofNullable(users.get(id));
    }
    
    @Override
    public List<User> findAll() {
        System.out.println("InMemoryUserRepository: Finding all users");
        return new ArrayList<>(users.values());
    }
    
    @Override
    public void delete(String id) {
        users.remove(id);
        System.out.println("InMemoryUserRepository: Deleted user " + id);
    }
    
    @Override
    public boolean exists(String id) {
        return users.containsKey(id);
    }
    
    @Override
    public List<User> findByName(String name) {
        System.out.println("InMemoryUserRepository: Finding users by name " + name);
        return users.values().stream()
                .filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<User> findByEmail(String email) {
        System.out.println("InMemoryUserRepository: Finding users by email " + email);
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .collect(Collectors.toList());
    }
}