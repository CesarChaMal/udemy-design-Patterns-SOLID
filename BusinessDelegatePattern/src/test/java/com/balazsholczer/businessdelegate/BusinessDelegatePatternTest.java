package com.balazsholczer.businessdelegate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

/**
 * Comprehensive test class for Business Delegate Pattern - Traditional vs Modern approaches
 */
class BusinessDelegatePatternTest {
    
    private BusinessDelegate businessDelegate;
    private Client client;
    
    @BeforeEach
    void setUp() {
        businessDelegate = new BusinessDelegate();
        client = new Client();
    }
    
    @Test
    void testTraditionalBusinessDelegate() {
        // Traditional business delegate pattern
        businessDelegate.setServiceType("order");
        String orderResult = businessDelegate.doTask("Order-123");
        
        assertNotNull(orderResult);
        assertTrue(orderResult.contains("Order processed"));
        assertTrue(orderResult.contains("Order-123"));
        
        // Switch to payment service
        businessDelegate.setServiceType("payment");
        String paymentResult = businessDelegate.doTask("Payment-456");
        
        assertNotNull(paymentResult);
        assertTrue(paymentResult.contains("Payment processed"));
        assertTrue(paymentResult.contains("Payment-456"));
        
        // Results should be different
        assertNotEquals(orderResult, paymentResult);
    }
    
    @Test
    void testClientUsage() {
        // Test client using business delegate
        assertDoesNotThrow(() -> client.processOrder("Order ABC"));
        assertDoesNotThrow(() -> client.processPayment("Payment XYZ"));
        
        // Client should handle both operations without knowing implementation details
        Client anotherClient = new Client();
        assertDoesNotThrow(() -> {
            anotherClient.processOrder("Test Order");
            anotherClient.processPayment("Test Payment");
        });
    }
    
    @Test
    void testBusinessLookup() {
        // Test business lookup service
        BusinessLookup lookup = new BusinessLookup();
        
        BusinessService orderService = lookup.getBusinessService("order");
        BusinessService paymentService = lookup.getBusinessService("payment");
        
        assertNotNull(orderService);
        assertNotNull(paymentService);
        assertTrue(orderService instanceof OrderService);
        assertTrue(paymentService instanceof PaymentService);
        
        // Test service execution
        String orderResult = orderService.processRequest("Test Order");
        String paymentResult = paymentService.processRequest("Test Payment");
        
        assertEquals("Order processed: Test Order", orderResult);
        assertEquals("Payment processed: Test Payment", paymentResult);
    }
    
    @Test
    void testServiceSwitching() {
        // Test dynamic service switching
        businessDelegate.setServiceType("order");
        String result1 = businessDelegate.doTask("Task 1");
        
        businessDelegate.setServiceType("payment");
        String result2 = businessDelegate.doTask("Task 1");
        
        // Same task, different services should produce different results
        assertNotEquals(result1, result2);
        assertTrue(result1.contains("Order processed"));
        assertTrue(result2.contains("Payment processed"));
    }
    
    @Test
    void testInvalidServiceType() {
        // Test error handling for invalid service type
        businessDelegate.setServiceType("invalid");
        
        assertThrows(IllegalArgumentException.class, () -> {
            businessDelegate.doTask("Test Task");
        });
    }
    
    @Test
    void testFunctionalApproach() {
        // Modern functional approach using lambdas
        Map<String, Function<String, String>> serviceMap = new HashMap<>();
        
        // Register functional services
        serviceMap.put("order", request -> "Functional Order: " + request);
        serviceMap.put("payment", request -> "Functional Payment: " + request);
        serviceMap.put("shipping", request -> "Functional Shipping: " + request);
        
        // Test functional services
        String orderResult = serviceMap.get("order").apply("Order-789");
        String paymentResult = serviceMap.get("payment").apply("Payment-101");
        String shippingResult = serviceMap.get("shipping").apply("Shipping-202");
        
        assertEquals("Functional Order: Order-789", orderResult);
        assertEquals("Functional Payment: Payment-101", paymentResult);
        assertEquals("Functional Shipping: Shipping-202", shippingResult);
    }
    
    @Test
    void testServiceComposition() {
        // Test composing multiple services
        Function<String, String> orderProcessor = request -> "Order: " + request;
        Function<String, String> paymentProcessor = request -> "Payment: " + request;
        Function<String, String> notificationProcessor = request -> "Notification: " + request;
        
        // Compose services
        Function<String, String> composedService = orderProcessor
            .andThen(result -> paymentProcessor.apply(result))
            .andThen(result -> notificationProcessor.apply(result));
        
        String result = composedService.apply("Test Request");
        assertEquals("Notification: Payment: Order: Test Request", result);
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Compare traditional and functional approaches
        businessDelegate.setServiceType("order");
        String traditionalResult = businessDelegate.doTask("Equivalence Test");
        
        Function<String, String> functionalOrder = request -> "Order processed: " + request;
        String functionalResult = functionalOrder.apply("Equivalence Test");
        
        // Both should process the same request
        assertEquals(traditionalResult, functionalResult);
    }
    
    @Test
    void testCaseInsensitiveServiceTypes() {
        // Test that service lookup is case insensitive
        businessDelegate.setServiceType("ORDER");
        String result1 = businessDelegate.doTask("Test");
        
        businessDelegate.setServiceType("order");
        String result2 = businessDelegate.doTask("Test");
        
        businessDelegate.setServiceType("Order");
        String result3 = businessDelegate.doTask("Test");
        
        // All should produce the same result
        assertEquals(result1, result2);
        assertEquals(result2, result3);
    }
    
    @Test
    void testServiceDecoupling() {
        // Test that client is decoupled from service implementations
        Client client1 = new Client();
        Client client2 = new Client();
        
        // Multiple clients can work independently
        assertDoesNotThrow(() -> {
            client1.processOrder("Client1 Order");
            client2.processPayment("Client2 Payment");
            client1.processPayment("Client1 Payment");
            client2.processOrder("Client2 Order");
        });
    }
    
    @Test
    void testModernStreamBasedProcessing() {
        // Modern approach using streams
        String[] requests = {"Order-1", "Order-2", "Order-3"};
        
        Function<String, String> orderProcessor = request -> "Processed: " + request;
        
        String[] results = java.util.Arrays.stream(requests)
            .map(orderProcessor)
            .toArray(String[]::new);
        
        assertEquals(3, results.length);
        assertEquals("Processed: Order-1", results[0]);
        assertEquals("Processed: Order-2", results[1]);
        assertEquals("Processed: Order-3", results[2]);
    }
}