package com.balazsholczer.unitofwork;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Comprehensive test class for Unit of Work Pattern - Traditional vs Modern approaches
 */
class UnitOfWorkPatternTest {
    
    private UnitOfWork unitOfWork;
    private Customer testCustomer;
    
    @BeforeEach
    void setUp() {
        unitOfWork = new UnitOfWork();
        testCustomer = new Customer("1", "John Doe", "john@example.com");
    }
    
    @Test
    void testTraditionalUnitOfWork() {
        // Traditional Unit of Work pattern
        Customer customer1 = new Customer("2", "Alice", "alice@example.com");
        Customer customer2 = new Customer("3", "Bob", "bob@example.com");
        
        // Register new entities
        unitOfWork.registerNew(customer1);
        unitOfWork.registerNew(customer2);
        
        // Modify existing entity
        customer1.setName("Alice Smith");
        unitOfWork.registerDirty(customer1);
        
        // Remove entity
        unitOfWork.registerRemoved(customer2);
        
        // Commit all changes in single transaction
        assertDoesNotThrow(() -> unitOfWork.commit());
        
        // Verify no pending changes after commit
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testFunctionalUnitOfWork() {
        // Functional approach using streams and lambdas
        List<Entity> entities = Arrays.asList(
            new Customer("4", "Customer1", "c1@example.com"),
            new Customer("5", "Customer2", "c2@example.com"),
            new Customer("6", "Customer3", "c3@example.com")
        );
        
        // Functional batch operations
        entities.forEach(unitOfWork::registerNew);
        
        // Functional filtering and processing
        entities.stream()
            .filter(e -> e instanceof Customer)
            .map(e -> (Customer) e)
            .filter(c -> c.getName().contains("1"))
            .forEach(c -> {
                c.setName(c.getName() + " Updated");
                unitOfWork.registerDirty(c);
            });
        
        assertDoesNotThrow(() -> unitOfWork.commit());
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testTransactionBoundaries() {
        // Test transaction isolation and boundaries
        Customer customer = new Customer("7", "Transaction Test", "trans@example.com");
        
        unitOfWork.registerNew(customer);
        assertTrue(unitOfWork.hasChanges());
        
        // Commit transaction
        unitOfWork.commit();
        
        // After commit, no pending changes
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testRollbackCapability() {
        // Test rollback functionality
        Customer customer1 = new Customer("8", "Rollback Test 1", "rb1@example.com");
        Customer customer2 = new Customer("9", "Rollback Test 2", "rb2@example.com");
        
        unitOfWork.registerNew(customer1);
        unitOfWork.registerNew(customer2);
        
        assertTrue(unitOfWork.hasChanges());
        
        // Rollback changes
        unitOfWork.rollback();
        
        // All changes should be discarded
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testEntityStateTracking() {
        // Test comprehensive entity state tracking
        Customer customer = new Customer("10", "State Test", "state@example.com");
        
        // New entity
        unitOfWork.registerNew(customer);
        assertTrue(unitOfWork.hasChanges());
        
        // Modify entity (becomes dirty)
        customer.setName("Modified State Test");
        unitOfWork.registerDirty(customer);
        assertTrue(customer.isDirty());
        
        // Remove entity
        unitOfWork.registerRemoved(customer);
        assertFalse(unitOfWork.hasChanges());
        
        // Commit to clear all changes
        unitOfWork.commit();
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testBatchOperations() {
        // Test batch processing capabilities
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(new Customer(String.valueOf(i + 11), "Customer " + i, "customer" + i + "@example.com"));
        }
        
        // Batch register new entities
        customers.forEach(unitOfWork::registerNew);
        assertTrue(unitOfWork.hasChanges());
        
        // Batch modify entities
        customers.stream()
            .filter(c -> c.getName().contains("5"))
            .forEach(c -> {
                c.setName(c.getName() + " Modified");
                unitOfWork.registerDirty(c);
            });
        
        // Batch remove some entities
        customers.stream()
            .filter(c -> c.getName().contains("9"))
            .forEach(unitOfWork::registerRemoved);
        
        // Commit all changes
        assertDoesNotThrow(() -> unitOfWork.commit());
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testConcurrentModification() {
        // Test handling of concurrent modifications
        Customer customer = new Customer("21", "Concurrent Test", "concurrent@example.com");
        
        unitOfWork.registerNew(customer);
        
        // Simulate concurrent modification
        customer.setName("Modified by Thread 1");
        unitOfWork.registerDirty(customer);
        
        customer.setName("Modified by Thread 2");
        unitOfWork.registerDirty(customer);
        
        // Should handle multiple modifications to same entity
        assertTrue(unitOfWork.hasChanges());
        assertTrue(customer.isDirty());
        assertEquals("Modified by Thread 2", customer.getName());
    }
    
    @Test
    void testMemoryManagement() {
        // Test memory efficiency with large datasets
        List<Customer> largeDataset = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            largeDataset.add(new Customer(String.valueOf(i + 22), "Customer " + i, "customer" + i + "@example.com"));
        }
        
        // Register all entities
        largeDataset.forEach(unitOfWork::registerNew);
        assertTrue(unitOfWork.hasChanges());
        
        // Commit and verify cleanup
        unitOfWork.commit();
        
        // Memory should be freed after commit
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testEntityRelationships() {
        // Test handling of related entities
        Customer customer = new Customer("122", "Parent Customer", "parent@example.com");
        Customer relatedCustomer = new Customer("123", "Related Customer", "related@example.com");
        
        // Register related entities
        unitOfWork.registerNew(customer);
        unitOfWork.registerNew(relatedCustomer);
        
        // Modify relationships
        customer.setName("Updated Parent");
        relatedCustomer.setName("Updated Related");
        
        unitOfWork.registerDirty(customer);
        unitOfWork.registerDirty(relatedCustomer);
        
        // Commit should handle all related changes
        assertDoesNotThrow(() -> unitOfWork.commit());
        assertFalse(unitOfWork.hasChanges());
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify traditional and functional approaches produce same results
        UnitOfWork traditionalUoW = new UnitOfWork();
        UnitOfWork functionalUoW = new UnitOfWork();
        
        Customer customer1 = new Customer("124", "Test Customer", "test@example.com");
        Customer customer2 = new Customer("125", "Test Customer", "test@example.com");
        
        // Traditional approach
        traditionalUoW.registerNew(customer1);
        traditionalUoW.commit();
        
        // Functional approach
        Optional.of(customer2)
            .ifPresent(functionalUoW::registerNew);
        functionalUoW.commit();
        
        // Both should result in committed state
        assertFalse(traditionalUoW.hasChanges());
        assertFalse(functionalUoW.hasChanges());
    }
    
    @Test
    void testEntityDirtyTracking() {
        // Test entity dirty state tracking
        Customer customer = new Customer("126", "Clean Customer", "clean@example.com");
        
        assertFalse(customer.isDirty());
        
        // Modify entity - should become dirty
        customer.setName("Dirty Customer");
        assertTrue(customer.isDirty());
        
        // Register and commit
        unitOfWork.registerNew(customer);
        unitOfWork.commit();
        
        // Should be clean after commit
        assertFalse(customer.isDirty());
    }
    
    @Test
    void testModernStreamBasedOperations() {
        // Modern functional approach with streams
        List<Customer> customers = Arrays.asList(
            new Customer("127", "Alice", "alice@test.com"),
            new Customer("128", "Bob", "bob@test.com"),
            new Customer("129", "Charlie", "charlie@test.com")
        );
        
        // Stream-based registration
        customers.stream()
            .filter(c -> c.getName().length() > 3)
            .forEach(unitOfWork::registerNew);
        
        // Stream-based modification
        customers.stream()
            .filter(c -> c.getName().startsWith("A"))
            .peek(c -> c.setName(c.getName() + " Updated"))
            .forEach(unitOfWork::registerDirty);
        
        assertTrue(unitOfWork.hasChanges());
        unitOfWork.commit();
        assertFalse(unitOfWork.hasChanges());
    }
}