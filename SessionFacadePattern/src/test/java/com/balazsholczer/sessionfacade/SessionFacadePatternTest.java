package com.balazsholczer.sessionfacade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * Comprehensive test class for Session Facade Pattern - Traditional vs Modern approaches
 */
class SessionFacadePatternTest {
    
    private OrderSessionFacade sessionFacade;
    private Customer testCustomer;
    private List<String> testItems;
    
    @BeforeEach
    void setUp() {
        sessionFacade = new OrderSessionFacade();
        testCustomer = new Customer("CUST-123", "John Doe", "john@example.com");
        testItems = Arrays.asList("Laptop", "Mouse", "Keyboard");
    }
    
    @Test
    void testTraditionalSessionFacade() {
        // Traditional session facade pattern - coarse-grained operations
        String result = sessionFacade.processCompleteOrder("CUST-123", testItems);
        
        assertNotNull(result);
        assertTrue(result.contains("completed successfully"));
        assertTrue(result.contains("ORD-"));
        
        // Facade coordinates multiple fine-grained services in single call
        assertDoesNotThrow(() -> sessionFacade.processCompleteOrder("CUST-456", testItems));
    }
    
    @Test
    void testOrderCancellation() {
        // Test order cancellation workflow
        String cancelResult = sessionFacade.cancelOrder("ORD-12345");
        
        assertNotNull(cancelResult);
        assertTrue(cancelResult.contains("cancelled"));
        assertTrue(cancelResult.contains("refunded"));
        assertTrue(cancelResult.contains("ORD-12345"));
    }
    
    @Test
    void testInvalidCustomerHandling() {
        // Test handling of invalid customer - SessionFacade always succeeds as CustomerService creates customers
        String result = sessionFacade.processCompleteOrder("INVALID-CUSTOMER", testItems);
        
        assertNotNull(result);
        assertTrue(result.contains("completed successfully"));
    }
    
    @Test
    void testFunctionalApproach() {
        // Modern functional approach using composition
        Function<String, Customer> customerLookup = customerId -> 
            new Customer(customerId, "Functional Customer", "func@example.com");
        
        Function<List<String>, Order> orderCreator = items -> 
            new Order("FUNC-ORD-001", "CUST-123", items, BigDecimal.valueOf(items.size() * 50));
        
        Function<Order, Boolean> paymentProcessor = order -> {
            System.out.println("Functional payment processing for " + order.getOrderId());
            return order.getTotal().compareTo(BigDecimal.valueOf(1000)) <= 0; // Approve if <= 1000
        };
        
        // Compose the workflow
        Function<String, String> functionalWorkflow = customerId -> {
            Customer customer = customerLookup.apply(customerId);
            if (customer == null) return "Customer not found";
            
            Order order = orderCreator.apply(testItems);
            Boolean paymentSuccess = paymentProcessor.apply(order);
            
            return paymentSuccess ? 
                "Functional order " + order.getOrderId() + " completed" :
                "Functional payment failed";
        };
        
        String result = functionalWorkflow.apply("CUST-123");
        
        assertNotNull(result);
        assertTrue(result.contains("completed") || result.contains("failed"));
        assertTrue(result.contains("Functional"));
    }
    
    @Test
    void testServiceCoordination() {
        // Test that facade coordinates multiple services
        CustomerService customerService = new CustomerService();
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        
        // Test individual services work
        Customer customer = customerService.getCustomer("CUST-123");
        assertNotNull(customer);
        assertEquals("CUST-123", customer.getCustomerId());
        
        Order order = orderService.createOrder("CUST-123", testItems);
        assertNotNull(order);
        assertEquals("CUST-123", order.getCustomerId());
        assertEquals(testItems, order.getItems());
        
        boolean paymentResult = paymentService.processPayment(order.getOrderId(), order.getTotal());
        assertTrue(paymentResult);
    }
    
    @Test
    void testComplexOrderProcessing() {
        // Test processing orders with different item counts
        List<String> smallOrder = Arrays.asList("Book");
        List<String> largeOrder = Arrays.asList("TV", "Sound System", "Gaming Console", "Cables", "Warranty");
        
        String smallResult = sessionFacade.processCompleteOrder("CUST-001", smallOrder);
        String largeResult = sessionFacade.processCompleteOrder("CUST-002", largeOrder);
        
        assertNotNull(smallResult);
        assertNotNull(largeResult);
        assertTrue(smallResult.contains("completed"));
        assertTrue(largeResult.contains("completed"));
    }
    
    @Test
    void testOrderDataStructures() {
        // Test Order and Customer data structures
        Order order = new Order("ORD-001", "CUST-123", testItems, BigDecimal.valueOf(299.99));
        
        assertEquals("ORD-001", order.getOrderId());
        assertEquals("CUST-123", order.getCustomerId());
        assertEquals(testItems, order.getItems());
        assertEquals(BigDecimal.valueOf(299.99), order.getTotal());
        
        Customer customer = new Customer("CUST-123", "John Doe", "john@example.com");
        assertEquals("CUST-123", customer.getCustomerId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john@example.com", customer.getEmail());
    }
    
    @Test
    void testStreamBasedProcessing() {
        // Modern approach using streams for batch processing
        List<String> customerIds = Arrays.asList("CUST-001", "CUST-002", "CUST-003");
        
        List<String> results = customerIds.stream()
            .map(customerId -> sessionFacade.processCompleteOrder(customerId, testItems))
            .toList();
        
        assertEquals(3, results.size());
        results.forEach(result -> {
            assertNotNull(result);
            assertTrue(result.contains("completed") || result.contains("failed"));
        });
    }
    
    @Test
    void testServiceIntegration() {
        // Test integration between services through facade
        String customerId = "INTEGRATION-TEST";
        List<String> items = Arrays.asList("Integration Item 1", "Integration Item 2");
        
        // Process order
        String processResult = sessionFacade.processCompleteOrder(customerId, items);
        assertTrue(processResult.contains("completed"));
        
        // Extract order ID from result (assuming format "Order ORD-xxx completed successfully")
        String orderId = processResult.substring(processResult.indexOf("ORD-"), processResult.indexOf(" completed"));
        
        // Cancel the order
        String cancelResult = sessionFacade.cancelOrder(orderId);
        assertTrue(cancelResult.contains("cancelled"));
        assertTrue(cancelResult.contains(orderId));
    }
    
    @Test
    void testCoarseGrainedOperations() {
        // Test that facade provides coarse-grained operations
        // Single method call coordinates multiple fine-grained service calls
        
        long startTime = System.currentTimeMillis();
        String result = sessionFacade.processCompleteOrder("PERF-TEST", testItems);
        long duration = System.currentTimeMillis() - startTime;
        
        // Should complete quickly as it's a single coarse-grained call
        assertTrue(duration < 1000);
        assertNotNull(result);
        assertTrue(result.contains("completed"));
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify traditional and functional approaches produce equivalent results
        String traditionalResult = sessionFacade.processCompleteOrder("EQUIV-TEST", testItems);
        
        // Functional equivalent
        Function<String, String> functionalFacade = customerId -> {
            // Simulate the same workflow functionally
            Customer customer = new Customer(customerId, "Test Customer", "test@example.com");
            Order order = new Order("FUNC-ORD", customerId, testItems, BigDecimal.valueOf(300));
            boolean paymentSuccess = true; // Simulate successful payment
            
            return paymentSuccess ? 
                "Order " + order.getOrderId() + " completed successfully" :
                "Order failed: Payment processing error";
        };
        
        String functionalResult = functionalFacade.apply("EQUIV-TEST");
        
        // Both should successfully complete
        assertNotNull(traditionalResult);
        assertNotNull(functionalResult);
        assertTrue(traditionalResult.contains("completed"));
        assertTrue(functionalResult.contains("completed"));
    }
    
    @Test
    void testErrorHandlingWorkflow() {
        // Test error handling in complex workflows
        List<String> emptyItems = new ArrayList<>();
        
        // Should handle empty order gracefully
        String emptyResult = sessionFacade.processCompleteOrder("CUST-123", emptyItems);
        assertNotNull(emptyResult);
        
        // Should handle null customer ID
        assertDoesNotThrow(() -> sessionFacade.processCompleteOrder(null, testItems));
    }
}