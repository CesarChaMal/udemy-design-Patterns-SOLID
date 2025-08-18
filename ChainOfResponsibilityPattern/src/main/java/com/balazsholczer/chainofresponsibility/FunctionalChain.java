package com.balazsholczer.chainofresponsibility;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalChain {
    
    public record RequestRecord(String type, String content, int priority) {}
    
    public record ChainHandler(Predicate<RequestRecord> canHandle, Function<RequestRecord, Boolean> handler) {
        
        public boolean handle(RequestRecord request) {
            if (canHandle.test(request)) {
                return handler.apply(request);
            }
            return false;
        }
    }
    
    public static final ChainHandler INFO_HANDLER = new ChainHandler(
        req -> "INFO".equals(req.type()),
        req -> {
            System.out.println("FunctionalChain: Processing INFO - " + req);
            return true;
        }
    );
    
    public static final ChainHandler WARNING_HANDLER = new ChainHandler(
        req -> "WARNING".equals(req.type()),
        req -> {
            System.out.println("FunctionalChain: Processing WARNING - " + req);
            return true;
        }
    );
    
    public static final ChainHandler ERROR_HANDLER = new ChainHandler(
        req -> "ERROR".equals(req.type()),
        req -> {
            System.out.println("FunctionalChain: Processing ERROR - " + req);
            return true;
        }
    );
    
    public static void processRequest(RequestRecord request, List<ChainHandler> handlers) {
        boolean handled = handlers.stream()
                                 .anyMatch(handler -> handler.handle(request));
        
        if (!handled) {
            System.out.println("FunctionalChain: No handler found for " + request);
        }
    }
}