package com.balazsholczer.chainofresponsibility;

import java.util.List;

public class ModernChainDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Chain of Responsibility Pattern ===");
        Handler infoHandler = new InfoHandler();
        Handler warningHandler = new WarningHandler();
        Handler errorHandler = new ErrorHandler();
        
        infoHandler.setNext(warningHandler);
        warningHandler.setNext(errorHandler);
        
        Request infoReq = new Request("INFO", "System started", 1);
        Request warningReq = new Request("WARNING", "Low memory", 3);
        Request errorReq = new Request("ERROR", "Database connection failed", 5);
        Request unknownReq = new Request("DEBUG", "Debug message", 2);
        
        infoHandler.handleRequest(infoReq);
        infoHandler.handleRequest(warningReq);
        infoHandler.handleRequest(errorReq);
        infoHandler.handleRequest(unknownReq);
        
        System.out.println("\n=== Functional Chain Pattern ===");
        var funcInfoReq = new FunctionalChain.RequestRecord("INFO", "System started", 1);
        var funcWarningReq = new FunctionalChain.RequestRecord("WARNING", "Low memory", 3);
        var funcErrorReq = new FunctionalChain.RequestRecord("ERROR", "Database connection failed", 5);
        var funcUnknownReq = new FunctionalChain.RequestRecord("DEBUG", "Debug message", 2);
        
        List<FunctionalChain.ChainHandler> handlers = List.of(
            FunctionalChain.INFO_HANDLER,
            FunctionalChain.WARNING_HANDLER,
            FunctionalChain.ERROR_HANDLER
        );
        
        FunctionalChain.processRequest(funcInfoReq, handlers);
        FunctionalChain.processRequest(funcWarningReq, handlers);
        FunctionalChain.processRequest(funcErrorReq, handlers);
        FunctionalChain.processRequest(funcUnknownReq, handlers);
        
        System.out.println("\n=== Enum Chain Pattern ===");
        var enumInfoReq = new EnumChain.RequestRecord("INFO", "System started", 1);
        var enumWarningReq = new EnumChain.RequestRecord("WARNING", "Low memory", 3);
        var enumErrorReq = new EnumChain.RequestRecord("ERROR", "Database connection failed", 5);
        var enumUnknownReq = new EnumChain.RequestRecord("DEBUG", "Debug message", 2);
        
        EnumChain.processRequest(enumInfoReq);
        EnumChain.processRequest(enumWarningReq);
        EnumChain.processRequest(enumErrorReq);
        EnumChain.processRequest(enumUnknownReq);
        
        System.out.println("\n=== Stream Chain Pattern ===");
        var streamInfoReq = new StreamChain.RequestRecord("INFO", "System started", 1);
        var streamWarningReq = new StreamChain.RequestRecord("WARNING", "Low memory", 3);
        var streamErrorReq = new StreamChain.RequestRecord("ERROR", "Database connection failed", 5);
        var streamPriorityReq = new StreamChain.RequestRecord("DEBUG", "Critical debug", 8);
        var streamUnknownReq = new StreamChain.RequestRecord("TRACE", "Trace message", 1);
        
        StreamChain.processRequest(streamInfoReq);
        StreamChain.processRequest(streamWarningReq);
        StreamChain.processRequest(streamErrorReq);
        StreamChain.processRequest(streamPriorityReq);
        StreamChain.processRequest(streamUnknownReq);
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - Custom Handler:");
        var customHandler = new FunctionalChain.ChainHandler(
            req -> req.priority() > 4,
            req -> {
                System.out.println("Custom: High priority " + req);
                return true;
            }
        );
        var customHandlers = List.of(customHandler, FunctionalChain.ERROR_HANDLER);
        FunctionalChain.processRequest(funcErrorReq, customHandlers);
        
        System.out.println("Stream - Process All Matching Handlers:");
        StreamChain.processAllHandlers(streamPriorityReq);
        
        System.out.println("Enum - All Available Handlers:");
        for (EnumChain handler : EnumChain.values()) {
            System.out.println("Handler: " + handler.name());
        }
    }
}