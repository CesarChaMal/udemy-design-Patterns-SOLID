package com.balazsholczer.chainofresponsibility;

/**
 * Chain of Responsibility Pattern: passes requests along a chain of handlers
 * 
 * Key Concepts:
 * - Decouples sender from receiver of a request
 * - Multiple objects can handle the request
 * - Chain is built dynamically at runtime
 * - Request travels until a handler processes it
 * 
 * Structure:
 * - Handler: defines interface for handling requests
 * - ConcreteHandler: handles requests it's responsible for
 * - Client: initiates request to chain
 * 
 * Benefits:
 * - Reduces coupling between sender and receiver
 * - Adds/removes handlers dynamically
 * - Flexible assignment of responsibilities
 * - Follows Single Responsibility Principle
 * 
 * Use Cases:
 * - Event handling systems (GUI events)
 * - Logging frameworks (different log levels)
 * - Authentication/authorization chains
 * - Request processing pipelines
 * - Exception handling hierarchies
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Chain of Responsibility Pattern ===");
        
        // Build the chain
        Handler infoHandler = new InfoHandler();
        Handler warningHandler = new WarningHandler();
        Handler errorHandler = new ErrorHandler();
        
        infoHandler.setNext(warningHandler);
        warningHandler.setNext(errorHandler);
        
        // Send requests through the chain
        Request infoReq = new Request("INFO", "System started", 1);
        Request warningReq = new Request("WARNING", "Low memory", 3);
        Request errorReq = new Request("ERROR", "Database failed", 5);
        Request unknownReq = new Request("DEBUG", "Debug info", 2);
        
        // Each request finds its appropriate handler
        infoHandler.handleRequest(infoReq);
        infoHandler.handleRequest(warningReq);
        infoHandler.handleRequest(errorReq);
        infoHandler.handleRequest(unknownReq);
        
        System.out.println("\n=== Run ModernChainDemo for all approaches ===");
    }
}