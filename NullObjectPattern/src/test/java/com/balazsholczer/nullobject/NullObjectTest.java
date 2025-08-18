package com.balazsholczer.nullobject;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Null Object Pattern
 * Tests Traditional, Functional, Optional, and Record approaches
 */
class NullObjectTest {
    
    @Test
    void testTraditionalNullObject() {
        CustomerFactory factory = new CustomerFactory();
        
        AbstractCustomer realCustomer = factory.getCustomer("Daniel");
        AbstractCustomer nullCustomer = factory.getCustomer("NonExistent");
        
        assertNotNull(realCustomer);
        assertNotNull(nullCustomer);
        
        assertTrue(realCustomer instanceof RealCustomer);
        assertTrue(nullCustomer instanceof NullCustomer);
        
        assertEquals("Daniel", realCustomer.getPerson());
        assertEquals("No person with the given name in the database...", nullCustomer.getPerson());
        
        assertFalse(realCustomer.isNull());
        assertTrue(nullCustomer.isNull());
    }
    
    @Test
    void testFunctionalCustomerFactory() {
        FunctionalCustomerFactory factory = new FunctionalCustomerFactory();
        
        String realCustomer = factory.getCustomerName("Adam");
        String nullCustomer = factory.getCustomerName("NonExistent");
        
        assertNotNull(realCustomer);
        assertNotNull(nullCustomer);
        
        assertEquals("Adam", realCustomer);
        assertEquals("No person with the given name in the database...", nullCustomer);
    }
    
    @Test
    void testOptionalCustomerFactory() {
        OptionalCustomerFactory factory = new OptionalCustomerFactory();
        
        Optional<String> existingCustomer = factory.getCustomer("Joe");
        Optional<String> nonExistingCustomer = factory.getCustomer("NonExistent");
        
        assertTrue(existingCustomer.isPresent());
        assertFalse(nonExistingCustomer.isPresent());
        
        assertEquals("Joe", existingCustomer.get());
        
        String customerName1 = factory.getCustomerName("Michael");
        String customerName2 = factory.getCustomerName("NonExistent");
        
        assertEquals("Michael", customerName1);
        assertEquals("No person with the given name in the database...", customerName2);
    }
    
    @Test
    void testRecordCustomerFactory() {
        RecordCustomerFactory factory = new RecordCustomerFactory();
        
        CustomerRecord realCustomer = factory.getCustomer("Daniel");
        CustomerRecord nullCustomer = factory.getCustomer("NonExistent");
        
        assertNotNull(realCustomer);
        assertNotNull(nullCustomer);
        
        assertEquals("Daniel", realCustomer.name());
        assertEquals("No person with the given name in the database...", nullCustomer.name());
        assertTrue(nullCustomer.isNull());
    }
    
    @Test
    void testNullObjectBehavior() {
        CustomerFactory factory = new CustomerFactory();
        AbstractCustomer nullCustomer = factory.getCustomer("NonExistent");
        
        // Null object should provide safe default behavior
        assertEquals("No person with the given name in the database...", nullCustomer.getPerson());
        assertTrue(nullCustomer.isNull());
        
        // Should not throw NullPointerException
        assertNotNull(nullCustomer.getPerson());
    }
    
    @Test
    void testDatabaseIntegration() {
        Database database = new Database();
        CustomerFactory factory = new CustomerFactory();
        
        // Test with existing customer
        AbstractCustomer existingCustomer = factory.getCustomer("Daniel");
        assertTrue(existingCustomer instanceof RealCustomer);
        
        // Test with non-existing customer
        AbstractCustomer nonExistingCustomer = factory.getCustomer("Unknown");
        assertTrue(nonExistingCustomer instanceof NullCustomer);
        
        // Both should work without null checks
        assertDoesNotThrow(() -> existingCustomer.getPerson());
        assertDoesNotThrow(() -> nonExistingCustomer.getPerson());
    }
    
    @Test
    void testNullCheckElimination() {
        // Traditional approach without null object pattern
        CustomerFactory factory = new CustomerFactory();
        
        // With null object pattern, no null checks needed
        AbstractCustomer customer1 = factory.getCustomer("Daniel");
        AbstractCustomer customer2 = factory.getCustomer("NonExistent");
        
        // Both are guaranteed to be non-null
        assertNotNull(customer1);
        assertNotNull(customer2);
        
        // Can safely call methods without null checks
        String name1 = customer1.getPerson();
        String name2 = customer2.getPerson();
        
        assertNotNull(name1);
        assertNotNull(name2);
        assertEquals("Daniel", name1);
        assertEquals("No person with the given name in the database...", name2);
    }
    
    @Test
    void testOptionalBehavior() {
        OptionalCustomerFactory factory = new OptionalCustomerFactory();
        
        // Test existing customer
        Optional<String> existing = factory.getCustomer("Adam");
        assertTrue(existing.isPresent());
        assertEquals("Adam", existing.get());
        
        // Test non-existing customer
        Optional<String> nonExisting = factory.getCustomer("NonExistent");
        assertFalse(nonExisting.isPresent());
        
        // Test safe access with orElse
        String name = nonExisting.orElse("Default Name");
        assertEquals("Default Name", name);
        
        // Test getCustomerName method
        assertEquals("Joe", factory.getCustomerName("Joe"));
        assertEquals("No person with the given name in the database...", factory.getCustomerName("NonExistent"));
    }
    
    @Test
    void testEquivalence() {
        // All approaches should handle null cases safely
        
        // Traditional
        CustomerFactory traditional = new CustomerFactory();
        AbstractCustomer traditionalReal = traditional.getCustomer("Daniel");
        AbstractCustomer traditionalNull = traditional.getCustomer("NonExistent");
        
        // Functional
        FunctionalCustomerFactory functional = new FunctionalCustomerFactory();
        String functionalReal = functional.getCustomerName("Daniel");
        String functionalNull = functional.getCustomerName("NonExistent");
        
        // Optional
        OptionalCustomerFactory optional = new OptionalCustomerFactory();
        String optionalReal = optional.getCustomerName("Daniel");
        String optionalNull = optional.getCustomerName("NonExistent");
        
        // Record
        RecordCustomerFactory record = new RecordCustomerFactory();
        CustomerRecord recordReal = record.getCustomer("Daniel");
        CustomerRecord recordNull = record.getCustomer("NonExistent");
        
        // All should handle real customers
        assertEquals("Daniel", traditionalReal.getPerson());
        assertEquals("Daniel", functionalReal);
        assertEquals("Daniel", optionalReal);
        assertEquals("Daniel", recordReal.name());
        
        // All should handle null cases safely
        assertEquals("No person with the given name in the database...", traditionalNull.getPerson());
        assertEquals("No person with the given name in the database...", functionalNull);
        assertEquals("No person with the given name in the database...", optionalNull);
        assertEquals("No person with the given name in the database...", recordNull.name());
        
        // All should be non-null objects/values
        assertNotNull(traditionalReal);
        assertNotNull(traditionalNull);
        assertNotNull(functionalReal);
        assertNotNull(functionalNull);
        assertNotNull(optionalReal);
        assertNotNull(optionalNull);
        assertNotNull(recordReal);
        assertNotNull(recordNull);
    }
    
    @Test
    void testAllDatabaseCustomers() {
        CustomerFactory factory = new CustomerFactory();
        String[] existingCustomers = {"Daniel", "Adam", "Michael", "Joe"};
        
        for (String customerName : existingCustomers) {
            AbstractCustomer customer = factory.getCustomer(customerName);
            assertNotNull(customer);
            assertFalse(customer.isNull());
            assertEquals(customerName, customer.getPerson());
        }
    }
}