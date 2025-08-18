package com.balazsholczer.solid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.balazsholczer.solid.improved.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Comprehensive test for Dependency Inversion Principle
 * Tests Traditional vs Improved approaches
 */
class SolidDTest {
    
    @Test
    void testTraditionalApproach() {
        // Traditional approach violates DIP - depends on concrete classes
        Database database = new MySQLDatabase();
        DatabaseHandler handler = new DatabaseHandler(database);
        
        // Handler is tightly coupled to specific database implementations
        assertDoesNotThrow(() -> handler.connect());
        assertDoesNotThrow(() -> handler.disconnect());
    }
    
    @Test
    void testImprovedApproach() {
        // Improved approach follows DIP - depends on abstractions
        DatabaseRepository<User> postgresRepo = new PostgreSQLRepository<>();
        DatabaseRepository<User> mongoRepo = new MongoDBRepository<>();
        DatabaseRepository<User> redisRepo = new RedisRepository<>();
        
        UserService postgresService = new UserService(postgresRepo);
        UserService mongoService = new UserService(mongoRepo);
        UserService redisService = new UserService(redisRepo);
        
        // All services work with different repositories
        assertDoesNotThrow(() -> postgresService.createUser("test", "test@example.com"));
        assertDoesNotThrow(() -> mongoService.createUser("test", "test@example.com"));
        assertDoesNotThrow(() -> redisService.createUser("test", "test@example.com"));
    }
    
    @Test
    void testDependencyInversionViolation() {
        // Traditional approach creates tight coupling
        Database database = new MySQLDatabase();
        DatabaseHandler handler = new DatabaseHandler(database);
        
        // Handler directly depends on concrete database implementation
        // Cannot be easily tested with mock database
        // Cannot switch database without modifying handler
        
        assertDoesNotThrow(() -> handler.connect());
        
        // Violates DIP - high-level module depends on low-level module
        assertTrue(database instanceof MySQLDatabase);
    }
    
    @Test
    void testDependencyInversionCompliance() {
        // Improved approach inverts dependencies
        DatabaseRepository<User> repository = new PostgreSQLRepository<>();
        UserService service = new UserService(repository);
        
        // High-level module (UserService) depends on abstraction (DatabaseRepository)
        // Low-level module (PostgreSQLRepository) depends on abstraction
        
        assertDoesNotThrow(() -> service.createUser("diptest", "dip@example.com"));
        
        // Can easily switch implementations
        DatabaseRepository<User> newRepository = new MongoDBRepository<>();
        UserService newService = new UserService(newRepository);
        assertDoesNotThrow(() -> newService.createUser("diptest2", "dip2@example.com"));
    }
    
    @Test
    void testAbstractionDependency() {
        // Test that both high-level and low-level modules depend on abstractions
        
        // High-level module depends on abstraction
        DatabaseRepository<User> abstraction = new PostgreSQLRepository<>();
        UserService highLevelModule = new UserService(abstraction);
        
        // Low-level module implements abstraction
        assertTrue(abstraction instanceof DatabaseRepository);
        
        assertDoesNotThrow(() -> highLevelModule.createUser("abstract", "abstract@example.com"));
        assertDoesNotThrow(() -> highLevelModule.getUser("test"));
    }
    
    @Test
    void testFlexibilityAndTestability() {
        // Improved approach enables easy testing and flexibility
        
        // Can use different implementations
        DatabaseRepository<User>[] repositories = new DatabaseRepository[]{
            new PostgreSQLRepository<User>(),
            new MongoDBRepository<User>(),
            new RedisRepository<User>()
        };
        
        for (DatabaseRepository<User> repo : repositories) {
            UserService service = new UserService(repo);
            assertDoesNotThrow(() -> service.createUser("flex", "flex@example.com"));
            assertDoesNotThrow(() -> service.getUser("test"));
        }
        
        // Easy to create mock for testing
        DatabaseRepository<User> mockRepo = new DatabaseRepository<User>() {
            @Override
            public CompletableFuture<Void> save(String id, User entity) {
                return CompletableFuture.completedFuture(null);
            }
            
            @Override
            public CompletableFuture<Optional<User>> findById(String id) {
                User mockUser = new User("mock", "Mock User", "mock@example.com", LocalDateTime.now());
                return CompletableFuture.completedFuture(Optional.of(mockUser));
            }
            
            @Override
            public CompletableFuture<Boolean> delete(String id) {
                return CompletableFuture.completedFuture(true);
            }
            
            @Override
            public CompletableFuture<Boolean> exists(String id) {
                return CompletableFuture.completedFuture(true);
            }
            
            @Override
            public String getRepositoryType() {
                return "Mock Repository";
            }
        };
        
        UserService testService = new UserService(mockRepo);
        assertDoesNotThrow(() -> testService.createUser("mock", "mock@example.com"));
        assertDoesNotThrow(() -> testService.getUser("mock"));
    }
    
    @Test
    void testEquivalence() {
        // Both approaches should handle database operations
        // but improved approach provides better flexibility
        
        // Traditional approach
        Database database = new MySQLDatabase();
        DatabaseHandler traditional = new DatabaseHandler(database);
        assertDoesNotThrow(() -> traditional.connect());
        assertDoesNotThrow(() -> traditional.disconnect());
        
        // Improved approach
        DatabaseRepository<User> repository = new PostgreSQLRepository<>();
        UserService improved = new UserService(repository);
        
        assertDoesNotThrow(() -> improved.createUser("improved", "improved@example.com"));
        assertDoesNotThrow(() -> improved.getUser("improved"));
        
        // Both work, but improved approach:
        // - Depends on abstractions, not concretions
        // - Enables easy testing with mocks
        // - Allows switching implementations without code changes
        // - Follows Dependency Inversion Principle
        
        // Traditional is tightly coupled
        assertTrue(database instanceof MySQLDatabase);
        
        // Improved is loosely coupled through abstraction
        assertTrue(repository instanceof DatabaseRepository);
    }
}