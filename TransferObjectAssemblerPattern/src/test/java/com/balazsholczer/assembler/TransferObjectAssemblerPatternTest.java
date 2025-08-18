package com.balazsholczer.assembler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;

/**
 * Comprehensive test class for Transfer Object Assembler Pattern - Testing actual implementation
 */
class TransferObjectAssemblerPatternTest {
    
    private CustomerAssembler customerAssembler;
    private FunctionalAssembler functionalAssembler;
    
    @BeforeEach
    void setUp() {
        customerAssembler = new CustomerAssembler();
        functionalAssembler = new FunctionalAssembler();
    }
    
    @Test
    void testTraditionalAssembler() {
        // Traditional assembler pattern - using valid customer ID
        String customerId = "1";
        
        CustomerTO customer = customerAssembler.assembleCustomer(customerId);
        
        assertNotNull(customer);
        assertEquals(customerId, customer.getCustomerId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john@example.com", customer.getEmail());
        assertNotNull(customer.getOrders());
        assertNotNull(customer.getAccount());
    }
    
    @Test
    void testBasicAssembly() {
        // Test basic customer assembly without related objects
        String customerId = "2";
        
        CustomerTO basicCustomer = customerAssembler.assembleCustomerBasic(customerId);
        
        assertNotNull(basicCustomer);
        assertEquals(customerId, basicCustomer.getCustomerId());
        assertEquals("Jane Smith", basicCustomer.getName());
        assertEquals("jane@example.com", basicCustomer.getEmail());
        // Orders and account should be null for basic assembly
        assertNull(basicCustomer.getOrders());
        assertNull(basicCustomer.getAccount());
    }
    
    @Test
    void testAssemblyWithOrders() {
        // Test customer assembly with orders only
        String customerId = "1";
        
        CustomerTO customerWithOrders = customerAssembler.assembleCustomerWithOrders(customerId);
        
        assertNotNull(customerWithOrders);
        assertEquals(customerId, customerWithOrders.getCustomerId());
        assertEquals("John Doe", customerWithOrders.getName());
        assertNotNull(customerWithOrders.getOrders());
        // Account should be null
        assertNull(customerWithOrders.getAccount());
    }
    
    @Test
    void testFunctionalBasicAssembler() {
        // Test functional basic assembler
        Function<String, CustomerTO> basicAssembler = functionalAssembler.basicAssembler();
        
        CustomerTO customer = basicAssembler.apply("2");
        
        assertNotNull(customer);
        assertEquals("2", customer.getCustomerId());
        assertEquals("Jane Smith", customer.getName());
        assertNull(customer.getOrders());
        assertNull(customer.getAccount());
    }
    
    @Test
    void testFunctionalWithOrders() {
        // Test functional assembler with orders
        Function<String, CustomerTO> withOrdersAssembler = functionalAssembler.withOrders();
        
        CustomerTO customer = withOrdersAssembler.apply("1");
        
        assertNotNull(customer);
        assertEquals("1", customer.getCustomerId());
        assertEquals("John Doe", customer.getName());
        assertNotNull(customer.getOrders());
        assertNull(customer.getAccount());
    }
    
    @Test
    void testFunctionalWithAccount() {
        // Test functional assembler with account
        Function<CustomerTO, CustomerTO> withAccountFunction = functionalAssembler.withAccount();
        
        // First get basic customer
        CustomerTO basicCustomer = functionalAssembler.basicAssembler().apply("2");
        
        // Then add account
        CustomerTO customerWithAccount = withAccountFunction.apply(basicCustomer);
        
        assertNotNull(customerWithAccount);
        assertEquals("2", customerWithAccount.getCustomerId());
        assertEquals("Jane Smith", customerWithAccount.getName());
        assertNotNull(customerWithAccount.getAccount());
    }
    
    @Test
    void testFunctionalFullAssembler() {
        // Test full functional assembler
        Function<String, CustomerTO> fullAssembler = functionalAssembler.fullAssembler();
        
        CustomerTO fullCustomer = fullAssembler.apply("1");
        
        assertNotNull(fullCustomer);
        assertEquals("1", fullCustomer.getCustomerId());
        assertEquals("John Doe", fullCustomer.getName());
        assertNotNull(fullCustomer.getOrders());
        assertNotNull(fullCustomer.getAccount());
    }
    
    @Test
    void testAssemblerBuilder() {
        // Test functional assembler builder pattern
        Function<String, CustomerTO> customAssembler = new FunctionalAssembler.AssemblerBuilder()
            .withOrders()
            .withAccount()
            .build();
        
        CustomerTO customer = customAssembler.apply("2");
        
        assertNotNull(customer);
        assertEquals("2", customer.getCustomerId());
        assertEquals("Jane Smith", customer.getName());
        assertNotNull(customer.getOrders());
        assertNotNull(customer.getAccount());
    }
    
    @Test
    void testAssemblerBuilderPartial() {
        // Test builder with only orders
        Function<String, CustomerTO> ordersOnlyAssembler = new FunctionalAssembler.AssemblerBuilder()
            .withOrders()
            .build();
        
        CustomerTO customer = ordersOnlyAssembler.apply("1");
        
        assertNotNull(customer);
        assertEquals("1", customer.getCustomerId());
        assertEquals("John Doe", customer.getName());
        assertNotNull(customer.getOrders());
        assertNull(customer.getAccount());
    }
    
    @Test
    void testAssemblerBuilderAccountOnly() {
        // Test builder with only account
        Function<String, CustomerTO> accountOnlyAssembler = new FunctionalAssembler.AssemblerBuilder()
            .withAccount()
            .build();
        
        CustomerTO customer = accountOnlyAssembler.apply("2");
        
        assertNotNull(customer);
        assertEquals("2", customer.getCustomerId());
        assertEquals("Jane Smith", customer.getName());
        assertNull(customer.getOrders());
        assertNotNull(customer.getAccount());
    }
    
    @Test
    void testNullCustomerHandling() {
        // Test handling of non-existent customer
        CustomerTO nullCustomer = customerAssembler.assembleCustomer("999");
        
        assertNull(nullCustomer);
    }
    
    @Test
    void testCustomerToString() {
        // Test CustomerTO toString method
        CustomerTO customer = customerAssembler.assembleCustomer("1");
        
        assertNotNull(customer);
        String customerString = customer.toString();
        
        assertNotNull(customerString);
        assertTrue(customerString.contains("1"));
        assertTrue(customerString.contains("CustomerTO"));
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify traditional and functional approaches produce equivalent results
        String customerId = "1";
        
        // Traditional approach
        CustomerTO traditionalCustomer = customerAssembler.assembleCustomer(customerId);
        
        // Functional approach
        CustomerTO functionalCustomer = functionalAssembler.fullAssembler().apply(customerId);
        
        // Both should have same basic data
        assertNotNull(traditionalCustomer);
        assertNotNull(functionalCustomer);
        assertEquals(traditionalCustomer.getCustomerId(), functionalCustomer.getCustomerId());
        assertEquals(traditionalCustomer.getName(), functionalCustomer.getName());
        assertEquals(traditionalCustomer.getEmail(), functionalCustomer.getEmail());
        
        // Both should have orders and account
        assertNotNull(traditionalCustomer.getOrders());
        assertNotNull(functionalCustomer.getOrders());
        assertNotNull(traditionalCustomer.getAccount());
        assertNotNull(functionalCustomer.getAccount());
    }
    
    @Test
    void testFunctionalComposition() {
        // Test functional composition capabilities
        Function<String, CustomerTO> composedAssembler = functionalAssembler.basicAssembler()
            .andThen(functionalAssembler.withAccount())
            .andThen(customer -> {
                if (customer != null) {
                    customer.setOrders(new OrderDAO().findByCustomerId(customer.getCustomerId()));
                }
                return customer;
            });
        
        CustomerTO customer = composedAssembler.apply("2");
        
        assertNotNull(customer);
        assertEquals("2", customer.getCustomerId());
        assertEquals("Jane Smith", customer.getName());
        assertNotNull(customer.getOrders());
        assertNotNull(customer.getAccount());
    }
    
    @Test
    void testBothCustomers() {
        // Test both available customers
        CustomerTO customer1 = customerAssembler.assembleCustomer("1");
        CustomerTO customer2 = customerAssembler.assembleCustomer("2");
        
        assertNotNull(customer1);
        assertNotNull(customer2);
        
        assertEquals("John Doe", customer1.getName());
        assertEquals("Jane Smith", customer2.getName());
        
        assertNotEquals(customer1.getCustomerId(), customer2.getCustomerId());
    }
}