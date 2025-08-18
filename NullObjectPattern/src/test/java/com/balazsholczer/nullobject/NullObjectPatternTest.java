package com.balazsholczer.nullobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Null Object Pattern
 * Tests Traditional approach with existing Database implementation
 */
class NullObjectPatternTest {
    
    @Test
    void testTraditionalNullObjectPattern() {
        CustomerFactory factory = new CustomerFactory();
        
        // Test existing customer
        AbstractCustomer existingCustomer = factory.getCustomer("Daniel");
        assertNotNull(existingCustomer);
        assertTrue(existingCustomer instanceof RealCustomer);
        assertFalse(existingCustomer.isNull());
        assertEquals("Daniel", existingCustomer.getPerson());
        
        // Test non-existing customer
        AbstractCustomer nonExistingCustomer = factory.getCustomer("NonExistent");
        assertNotNull(nonExistingCustomer);
        assertTrue(nonExistingCustomer instanceof NullCustomer);
        assertTrue(nonExistingCustomer.isNull());
        assertEquals("No person with the given name in the database...", nonExistingCustomer.getPerson());
    }
    
    @Test
    void testDatabaseBehavior() {
        Database database = new Database();
        
        // Test existing customers
        assertTrue(database.existCustomer("Daniel"));
        assertTrue(database.existCustomer("Adam"));
        assertTrue(database.existCustomer("Michael"));
        assertTrue(database.existCustomer("Joe"));
        
        // Test non-existing customer
        assertFalse(database.existCustomer("NonExistent"));
        assertFalse(database.existCustomer("Unknown"));
    }
    
    @Test
    void testNullObjectBehavior() {
        CustomerFactory factory = new CustomerFactory();
        
        // Get null object
        AbstractCustomer nullCustomer = factory.getCustomer("NonExistent");
        
        // Should not throw exceptions
        assertDoesNotThrow(() -> nullCustomer.getPerson());
        assertDoesNotThrow(() -> nullCustomer.isNull());
        
        // Should provide meaningful defaults
        assertEquals("No person with the given name in the database...", nullCustomer.getPerson());
        assertTrue(nullCustomer.isNull());
        
        // Should be identifiable as null object
        assertTrue(nullCustomer instanceof NullCustomer);
    }
    
    @Test
    void testRealCustomerBehavior() {
        CustomerFactory factory = new CustomerFactory();
        
        // Get real customer
        AbstractCustomer realCustomer = factory.getCustomer("Adam");
        
        assertNotNull(realCustomer);
        assertTrue(realCustomer instanceof RealCustomer);
        assertEquals("Adam", realCustomer.getPerson());
        assertFalse(realCustomer.isNull());
    }
    
    @Test
    void testPolymorphicBehavior() {
        CustomerFactory factory = new CustomerFactory();
        
        // Test that null objects work polymorphically
        AbstractCustomer[] customers = {
            factory.getCustomer("Daniel"),
            factory.getCustomer("NonExistent"),
            factory.getCustomer("Adam"),
            factory.getCustomer("AnotherNonExistent")
        };
        
        // All can be treated uniformly
        for (AbstractCustomer customer : customers) {
            assertNotNull(customer);
            assertNotNull(customer.getPerson());
            
            // No null checks needed
            assertDoesNotThrow(() -> customer.getPerson());
            assertDoesNotThrow(() -> customer.isNull());
        }
        
        // Count real vs null customers
        long realCustomers = java.util.Arrays.stream(customers)
            .filter(c -> !c.isNull())
            .count();
        long nullCustomers = java.util.Arrays.stream(customers)
            .filter(AbstractCustomer::isNull)
            .count();
        
        assertEquals(2, realCustomers);
        assertEquals(2, nullCustomers);
    }
    
    @Test
    void testNullObjectVsNullReference() {
        CustomerFactory factory = new CustomerFactory();
        
        // Null object is safe to use
        AbstractCustomer nullObject = factory.getCustomer("NonExistent");
        assertDoesNotThrow(() -> {
            String person = nullObject.getPerson();
            boolean isNull = nullObject.isNull();
            assertNotNull(person);
            assertTrue(isNull);
        });
        
        // Demonstrate no null checks needed
        String person = nullObject.getPerson();
        boolean isNull = nullObject.isNull();
        
        assertEquals("No person with the given name in the database...", person);
        assertTrue(isNull);
    }
    
    @Test
    void testAllDatabaseCustomers() {
        CustomerFactory factory = new CustomerFactory();
        
        // Test all predefined customers
        String[] existingCustomers = {"Daniel", "Adam", "Michael", "Joe"};
        
        for (String customerName : existingCustomers) {
            AbstractCustomer customer = factory.getCustomer(customerName);
            assertNotNull(customer);
            assertTrue(customer instanceof RealCustomer);
            assertEquals(customerName, customer.getPerson());
            assertFalse(customer.isNull());
        }
    }
    
    @Test
    void testFactoryConsistency() {
        CustomerFactory factory = new CustomerFactory();
        
        // Multiple calls should return consistent results
        AbstractCustomer customer1 = factory.getCustomer("Daniel");
        AbstractCustomer customer2 = factory.getCustomer("Daniel");
        
        assertEquals(customer1.getPerson(), customer2.getPerson());
        assertEquals(customer1.getClass(), customer2.getClass());
        assertEquals(customer1.isNull(), customer2.isNull());
        
        AbstractCustomer nullCustomer1 = factory.getCustomer("NonExistent");
        AbstractCustomer nullCustomer2 = factory.getCustomer("NonExistent");
        
        assertEquals(nullCustomer1.getPerson(), nullCustomer2.getPerson());
        assertEquals(nullCustomer1.getClass(), nullCustomer2.getClass());
        assertEquals(nullCustomer1.isNull(), nullCustomer2.isNull());
    }
    
    @Test
    void testNullObjectPattern() {
        // Demonstrate the main benefit: no null checks needed
        CustomerFactory factory = new CustomerFactory();
        
        // Process customers without knowing if they exist
        String[] customerNames = {"Daniel", "NonExistent", "Adam", "Unknown", "Joe"};
        
        for (String name : customerNames) {
            AbstractCustomer customer = factory.getCustomer(name);
            
            // Safe to call methods without null checks
            String customerName = customer.getPerson();
            boolean isNull = customer.isNull();
            
            assertNotNull(customerName);
            
            // Can process uniformly
            String info = "Customer: " + customerName + ", IsNull: " + isNull;
            assertNotNull(info);
        }
    }
    
    @Test
    void testIsNullMethod() {
        CustomerFactory factory = new CustomerFactory();
        
        // Real customers should return false for isNull()
        AbstractCustomer realCustomer = factory.getCustomer("Daniel");
        assertFalse(realCustomer.isNull());
        
        // Null customers should return true for isNull()
        AbstractCustomer nullCustomer = factory.getCustomer("NonExistent");
        assertTrue(nullCustomer.isNull());
    }
}