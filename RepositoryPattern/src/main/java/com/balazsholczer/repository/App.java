package com.balazsholczer.repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Pattern: encapsulates data access logic
 * 
 * Key Concepts:
 * - Encapsulates logic needed to access data sources
 * - Centralizes common data access functionality
 * - Provides better maintainability and decouples infrastructure
 * - Domain-driven design pattern for data access abstraction
 * 
 * Benefits:
 * - Clean separation between domain and data access layers
 * - Easy to unit test with mock repositories
 * - Consistent data access interface
 * - Technology-agnostic domain layer
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Repository Pattern ===");
        System.out.println("Encapsulating data access logic");
        System.out.println();
        
        UserRepository userRepository = new InMemoryUserRepository();
        
        // Save users
        userRepository.save(new User("1", "John Doe", "john@example.com"));
        userRepository.save(new User("2", "Jane Smith", "jane@example.com"));
        userRepository.save(new User("3", "John Wilson", "johnw@example.com"));
        
        // Find operations
        Optional<User> user = userRepository.findById("1");
        System.out.println("Found user: " + user.orElse(null));
        
        List<User> allUsers = userRepository.findAll();
        System.out.println("All users: " + allUsers.size());
        
        List<User> johnUsers = userRepository.findByName("John");
        System.out.println("Users named John: " + johnUsers);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Clean data access abstraction");
        System.out.println("✅ Domain-driven design compliance");
        System.out.println("✅ Easy to test and mock");
        System.out.println("✅ Technology-agnostic interface");
    }
}