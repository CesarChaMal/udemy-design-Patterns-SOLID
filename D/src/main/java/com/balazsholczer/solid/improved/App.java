package com.balazsholczer.solid.improved;

import java.util.Optional;

/**
 * Modern Dependency Inversion Principle Implementation
 * 
 * Improvements:
 * - Constructor-based dependency injection
 * - Async operations with CompletableFuture
 * - Immutable entities with proper validation
 * - Generic repository pattern
 * - Thread-safe concurrent operations
 * - Modern Java features integration
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Modern Dependency Inversion Principle ===");
        System.out.println("Improved with dependency injection and async operations");
        System.out.println();
        
        try {
            // Test with different database implementations
            testWithPostgreSQL();
            testWithMongoDB();
            testWithRedis();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Modern DIP Benefits ===");
        System.out.println("✅ Constructor-based dependency injection");
        System.out.println("✅ Async operations with CompletableFuture");
        System.out.println("✅ Immutable entities with validation");
        System.out.println("✅ Generic repository pattern");
        System.out.println("✅ Easy database switching without code changes");
    }
    
    private static void testWithPostgreSQL() {
        System.out.println("--- Testing with PostgreSQL ---");
        DatabaseRepository<User> repository = new PostgreSQLRepository<>();
        UserService userService = new UserService(repository);
        
        testUserOperations(userService);
    }
    
    private static void testWithMongoDB() {
        System.out.println("\n--- Testing with MongoDB ---");
        DatabaseRepository<User> repository = new MongoDBRepository<>();
        UserService userService = new UserService(repository);
        
        testUserOperations(userService);
    }
    
    private static void testWithRedis() {
        System.out.println("\n--- Testing with Redis ---");
        DatabaseRepository<User> repository = new RedisRepository<>();
        UserService userService = new UserService(repository);
        
        testUserOperations(userService);
    }
    
    private static void testUserOperations(UserService userService) {
        try {
            // Create user
            User user = userService.createUser("john_doe", "john@example.com").join();
            System.out.println("Created: " + user);
            
            // Get user
            Optional<User> retrieved = userService.getUser(user.getId()).join();
            retrieved.ifPresent(u -> System.out.println("Retrieved: " + u));
            
            // Check existence
            boolean exists = userService.userExists(user.getId()).join();
            System.out.println("Exists: " + exists);
            
            // Delete user
            boolean deleted = userService.deleteUser(user.getId()).join();
            System.out.println("Deleted: " + deleted);
            
        } catch (Exception e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
    }
}