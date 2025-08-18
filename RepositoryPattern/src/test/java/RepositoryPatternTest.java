package com.balazsholczer.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive test for Repository Pattern
 * Tests Traditional Repository with Generic Interface
 */
class RepositoryPatternTest {

    private UserRepository userRepository;
    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        testUser = new User("1", "John Doe", "john@example.com");
    }

    @Test
    void testSaveUser() {
        assertDoesNotThrow(() -> userRepository.save(testUser));
        assertTrue(userRepository.exists("1"));
    }

    @Test
    void testFindById() {
        userRepository.save(testUser);
        Optional<User> found = userRepository.findById("1");
        
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<User> notFound = userRepository.findById("999");
        assertFalse(notFound.isPresent());
    }

    @Test
    void testFindAll() {
        User user2 = new User("2", "Jane Doe", "jane@example.com");
        userRepository.save(testUser);
        userRepository.save(user2);
        
        List<User> allUsers = userRepository.findAll();
        assertEquals(2, allUsers.size());
    }

    @Test
    void testDelete() {
        userRepository.save(testUser);
        assertTrue(userRepository.findById("1").isPresent());
        
        userRepository.delete("1");
        assertFalse(userRepository.findById("1").isPresent());
    }

    @Test
    void testFindByEmail() {
        userRepository.save(testUser);
        List<User> found = userRepository.findByEmail("john@example.com");
        
        assertEquals(1, found.size());
        assertEquals("John Doe", found.get(0).getName());
    }

    @Test
    void testFindByName() {
        userRepository.save(testUser);
        List<User> found = userRepository.findByName("John");
        
        assertEquals(1, found.size());
        assertEquals("John Doe", found.get(0).getName());
    }

    @Test
    void testExists() {
        assertFalse(userRepository.exists("1"));
        
        userRepository.save(testUser);
        assertTrue(userRepository.exists("1"));
        
        userRepository.delete("1");
        assertFalse(userRepository.exists("1"));
    }

    @Test
    void testRepositoryAbstraction() {
        // Test that we can work with the repository interface
        UserRepository repo = new InMemoryUserRepository();
        assertNotNull(repo);
        
        // Repository should handle business logic abstraction
        User user = new User("test1", "Test User", "test@example.com");
        assertDoesNotThrow(() -> repo.save(user));
        
        Optional<User> retrieved = repo.findById("test1");
        assertTrue(retrieved.isPresent());
        assertEquals(user.getEmail(), retrieved.get().getEmail());
    }

    @Test
    void testGenericRepositoryInterface() {
        // Test generic Repository interface methods
        Repository<User, String> genericRepo = new InMemoryUserRepository();
        
        User user = new User("generic1", "Generic User", "generic@example.com");
        assertDoesNotThrow(() -> genericRepo.save(user));
        
        assertTrue(genericRepo.exists("generic1"));
        
        Optional<User> found = genericRepo.findById("generic1");
        assertTrue(found.isPresent());
        assertEquals("Generic User", found.get().getName());
        
        List<User> all = genericRepo.findAll();
        assertTrue(all.size() >= 1);
        
        genericRepo.delete("generic1");
        assertFalse(genericRepo.exists("generic1"));
    }

    @Test
    void testUserRepositorySpecificMethods() {
        // Test UserRepository specific methods
        User user1 = new User("u1", "Alice Smith", "alice@example.com");
        User user2 = new User("u2", "Bob Smith", "bob@example.com");
        User user3 = new User("u3", "Charlie Jones", "alice@example.com"); // Same email
        
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        
        // Test findByName
        List<User> smiths = userRepository.findByName("Smith");
        assertEquals(2, smiths.size());
        
        // Test findByEmail
        List<User> aliceEmails = userRepository.findByEmail("alice@example.com");
        assertEquals(2, aliceEmails.size());
        
        List<User> bobEmails = userRepository.findByEmail("bob@example.com");
        assertEquals(1, bobEmails.size());
        assertEquals("Bob Smith", bobEmails.get(0).getName());
    }

    @Test
    void testRepositoryPatternBenefits() {
        // Test that repository pattern provides abstraction over data access
        UserRepository repo = new InMemoryUserRepository();
        
        // Can easily switch implementations without changing client code
        assertNotNull(repo);
        
        // Provides consistent interface for data operations
        User user = new User("benefit1", "Benefit User", "benefit@example.com");
        repo.save(user);
        
        assertTrue(repo.exists("benefit1"));
        assertEquals(1, repo.findByName("Benefit").size());
        assertEquals(1, repo.findByEmail("benefit@example.com").size());
        
        repo.delete("benefit1");
        assertFalse(repo.exists("benefit1"));
    }
}