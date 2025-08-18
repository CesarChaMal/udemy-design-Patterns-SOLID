package com.balazsholczer.chainofresponsibility;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Chain of Responsibility Pattern
 * Tests Traditional, Functional, Enum, and Stream approaches
 */
class ChainOfResponsibilityTest {
    
    @Test
    void testTraditionalChain() {
        InfoHandler infoHandler = new InfoHandler();
        WarningHandler warningHandler = new WarningHandler();
        ErrorHandler errorHandler = new ErrorHandler();
        
        infoHandler.setNext(warningHandler);
        warningHandler.setNext(errorHandler);
        
        Request infoRequest = new Request("INFO", "Info message", 1);
        Request warningRequest = new Request("WARNING", "Warning message", 3);
        Request errorRequest = new Request("ERROR", "Error message", 5);
        
        assertDoesNotThrow(() -> infoHandler.handleRequest(infoRequest));
        assertDoesNotThrow(() -> infoHandler.handleRequest(warningRequest));
        assertDoesNotThrow(() -> infoHandler.handleRequest(errorRequest));
    }
    
    @Test
    void testFunctionalChain() {
        FunctionalChain.RequestRecord infoRequest = 
            new FunctionalChain.RequestRecord("INFO", "Info message", 1);
        FunctionalChain.RequestRecord warningRequest = 
            new FunctionalChain.RequestRecord("WARNING", "Warning message", 3);
        FunctionalChain.RequestRecord errorRequest = 
            new FunctionalChain.RequestRecord("ERROR", "Error message", 5);
        
        List<FunctionalChain.ChainHandler> handlers = List.of(
            FunctionalChain.INFO_HANDLER,
            FunctionalChain.WARNING_HANDLER,
            FunctionalChain.ERROR_HANDLER
        );
        
        assertDoesNotThrow(() -> FunctionalChain.processRequest(infoRequest, handlers));
        assertDoesNotThrow(() -> FunctionalChain.processRequest(warningRequest, handlers));
        assertDoesNotThrow(() -> FunctionalChain.processRequest(errorRequest, handlers));
    }
    
    @Test
    void testEnumChain() {
        EnumChain.RequestRecord infoRequest = 
            new EnumChain.RequestRecord("INFO", "Info message", 1);
        EnumChain.RequestRecord warningRequest = 
            new EnumChain.RequestRecord("WARNING", "Warning message", 3);
        EnumChain.RequestRecord errorRequest = 
            new EnumChain.RequestRecord("ERROR", "Error message", 5);
        
        assertDoesNotThrow(() -> EnumChain.processRequest(infoRequest));
        assertDoesNotThrow(() -> EnumChain.processRequest(warningRequest));
        assertDoesNotThrow(() -> EnumChain.processRequest(errorRequest));
    }
    
    @Test
    void testStreamChain() {
        StreamChain.RequestRecord infoRequest = 
            new StreamChain.RequestRecord("INFO", "Info message", 1);
        StreamChain.RequestRecord warningRequest = 
            new StreamChain.RequestRecord("WARNING", "Warning message", 3);
        StreamChain.RequestRecord errorRequest = 
            new StreamChain.RequestRecord("ERROR", "Error message", 5);
        StreamChain.RequestRecord priorityRequest = 
            new StreamChain.RequestRecord("CUSTOM", "High priority", 10);
        
        assertDoesNotThrow(() -> StreamChain.processRequest(infoRequest));
        assertDoesNotThrow(() -> StreamChain.processRequest(warningRequest));
        assertDoesNotThrow(() -> StreamChain.processRequest(errorRequest));
        assertDoesNotThrow(() -> StreamChain.processRequest(priorityRequest));
    }
    
    @Test
    void testRequestCreation() {
        Request request = new Request("INFO", "Test message", 2);
        
        assertEquals("INFO", request.getType());
        assertEquals("Test message", request.getContent());
        assertEquals(2, request.getPriority());
        assertNotNull(request.toString());
    }
    
    @Test
    void testFunctionalHandlerBehavior() {
        FunctionalChain.RequestRecord matchingRequest = 
            new FunctionalChain.RequestRecord("INFO", "Test", 1);
        FunctionalChain.RequestRecord nonMatchingRequest = 
            new FunctionalChain.RequestRecord("UNKNOWN", "Test", 1);
        
        assertTrue(FunctionalChain.INFO_HANDLER.handle(matchingRequest));
        assertFalse(FunctionalChain.INFO_HANDLER.handle(nonMatchingRequest));
    }
    
    @Test
    void testEnumHandlerBehavior() {
        EnumChain.RequestRecord matchingRequest = 
            new EnumChain.RequestRecord("WARNING", "Test", 1);
        EnumChain.RequestRecord nonMatchingRequest = 
            new EnumChain.RequestRecord("UNKNOWN", "Test", 1);
        
        assertTrue(EnumChain.WARNING.handle(matchingRequest));
        assertFalse(EnumChain.WARNING.handle(nonMatchingRequest));
    }
    
    @Test
    void testStreamChainAdvanced() {
        StreamChain.RequestRecord unknownRequest = 
            new StreamChain.RequestRecord("UNKNOWN", "Unknown type", 1);
        StreamChain.RequestRecord highPriorityRequest = 
            new StreamChain.RequestRecord("CUSTOM", "High priority", 8);
        
        // Should handle unknown requests gracefully
        assertDoesNotThrow(() -> StreamChain.processRequest(unknownRequest));
        
        // Should handle high priority requests
        assertDoesNotThrow(() -> StreamChain.processRequest(highPriorityRequest));
        
        // Test processing all handlers
        assertDoesNotThrow(() -> StreamChain.processAllHandlers(highPriorityRequest));
    }
    
    @Test
    void testChainOrdering() {
        // Traditional chain should process in order
        InfoHandler first = new InfoHandler();
        WarningHandler second = new WarningHandler();
        ErrorHandler third = new ErrorHandler();
        
        first.setNext(second);
        second.setNext(third);
        
        // Each request type should be handled by appropriate handler
        Request infoRequest = new Request("INFO", "Info", 1);
        Request warningRequest = new Request("WARNING", "Warning", 2);
        Request errorRequest = new Request("ERROR", "Error", 3);
        
        assertDoesNotThrow(() -> first.handleRequest(infoRequest));
        assertDoesNotThrow(() -> first.handleRequest(warningRequest));
        assertDoesNotThrow(() -> first.handleRequest(errorRequest));
    }
    
    @Test
    void testEquivalence() {
        // All approaches should handle same request types
        Request traditionalRequest = new Request("INFO", "Test message", 1);
        FunctionalChain.RequestRecord functionalRequest = 
            new FunctionalChain.RequestRecord("INFO", "Test message", 1);
        EnumChain.RequestRecord enumRequest = 
            new EnumChain.RequestRecord("INFO", "Test message", 1);
        StreamChain.RequestRecord streamRequest = 
            new StreamChain.RequestRecord("INFO", "Test message", 1);
        
        // All should process without throwing
        InfoHandler traditional = new InfoHandler();
        assertDoesNotThrow(() -> traditional.handleRequest(traditionalRequest));
        
        List<FunctionalChain.ChainHandler> handlers = List.of(FunctionalChain.INFO_HANDLER);
        assertDoesNotThrow(() -> FunctionalChain.processRequest(functionalRequest, handlers));
        
        assertDoesNotThrow(() -> EnumChain.processRequest(enumRequest));
        assertDoesNotThrow(() -> StreamChain.processRequest(streamRequest));
    }
    
    @Test
    void testUnhandledRequests() {
        // Test how each approach handles unknown request types
        FunctionalChain.RequestRecord unknownFunctional = 
            new FunctionalChain.RequestRecord("UNKNOWN", "Test", 1);
        EnumChain.RequestRecord unknownEnum = 
            new EnumChain.RequestRecord("UNKNOWN", "Test", 1);
        StreamChain.RequestRecord unknownStream = 
            new StreamChain.RequestRecord("UNKNOWN", "Test", 1);
        
        List<FunctionalChain.ChainHandler> handlers = List.of(
            FunctionalChain.INFO_HANDLER,
            FunctionalChain.WARNING_HANDLER,
            FunctionalChain.ERROR_HANDLER
        );
        
        assertDoesNotThrow(() -> FunctionalChain.processRequest(unknownFunctional, handlers));
        assertDoesNotThrow(() -> EnumChain.processRequest(unknownEnum));
        assertDoesNotThrow(() -> StreamChain.processRequest(unknownStream));
    }
}