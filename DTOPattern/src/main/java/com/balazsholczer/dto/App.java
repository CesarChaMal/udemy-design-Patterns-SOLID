package com.balazsholczer.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) Pattern: transfers data between layers/systems
 * 
 * Key Concepts:
 * - Carries data between processes to reduce method calls
 * - Encapsulates serialization logic
 * - Provides different views of the same data
 * - Separates internal representation from external API
 * 
 * Benefits:
 * - Reduces network calls by batching data
 * - Hides internal object structure
 * - Provides security by excluding sensitive fields
 * - Enables API versioning and backward compatibility
 * - Improves performance by transferring only needed data
 * 
 * Use Cases:
 * - REST API responses (exclude sensitive data)
 * - Remote service calls (reduce network overhead)
 * - Layer separation (presentation vs business logic)
 * - Data serialization (JSON, XML)
 * - Database query results (specific field sets)
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional DTO Pattern ===");
        
        // Create a user entity with all fields (including sensitive ones)
        User user = new User(1L, "john_doe", "john@example.com", "password123", 
                           LocalDateTime.now().minusDays(30), LocalDateTime.now().minusHours(2), true);
        
        System.out.println("Full Entity: " + user);
        
        // Convert to DTO for external API (excludes password, timestamps)
        UserDTO userDTO = UserMapper.toDTO(user);
        System.out.println("Public DTO: " + userDTO);
        
        // Demonstrate batch conversion
        List<User> users = List.of(user, 
            new User(2L, "jane_smith", "jane@example.com", "secret456", 
                   LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(1), true));
        
        List<UserDTO> userDTOs = UserMapper.toDTOList(users);
        System.out.println("Batch DTOs: " + userDTOs.size() + " users converted");
        
        System.out.println("\n=== Run ModernDTODemo for all approaches ===");
    }
}