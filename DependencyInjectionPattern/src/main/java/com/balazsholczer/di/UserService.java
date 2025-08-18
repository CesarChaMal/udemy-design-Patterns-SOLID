package com.balazsholczer.di;

import java.util.List;

public class UserService {
    
    private final UserRepository userRepository;
    
    // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("UserService: Injected " + userRepository.getClass().getSimpleName());
    }
    
    public void createUser(String id, String name, String email) {
        User user = new User(id, name, email);
        userRepository.save(user);
        System.out.println("UserService: User created - " + id);
    }
    
    public User getUser(String id) {
        System.out.println("UserService: Getting user - " + id);
        return userRepository.findById(id);
    }
    
    public List<User> getAllUsers() {
        System.out.println("UserService: Getting all users");
        return userRepository.findAll();
    }
}