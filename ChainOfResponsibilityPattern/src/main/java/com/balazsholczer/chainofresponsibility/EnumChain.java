package com.balazsholczer.chainofresponsibility;

import java.util.function.Function;
import java.util.function.Predicate;

public enum EnumChain {
    INFO(
        req -> "INFO".equals(req.type()),
        req -> {
            System.out.println("EnumChain: Processing INFO - " + req);
            return true;
        }
    ),
    WARNING(
        req -> "WARNING".equals(req.type()),
        req -> {
            System.out.println("EnumChain: Processing WARNING - " + req);
            return true;
        }
    ),
    ERROR(
        req -> "ERROR".equals(req.type()),
        req -> {
            System.out.println("EnumChain: Processing ERROR - " + req);
            return true;
        }
    );
    
    private final Predicate<RequestRecord> canHandle;
    private final Function<RequestRecord, Boolean> handler;
    
    EnumChain(Predicate<RequestRecord> canHandle, Function<RequestRecord, Boolean> handler) {
        this.canHandle = canHandle;
        this.handler = handler;
    }
    
    public boolean handle(RequestRecord request) {
        if (canHandle.test(request)) {
            return handler.apply(request);
        }
        return false;
    }
    
    public record RequestRecord(String type, String content, int priority) {}
    
    public static void processRequest(RequestRecord request) {
        boolean handled = false;
        for (EnumChain handler : values()) {
            if (handler.handle(request)) {
                handled = true;
                break;
            }
        }
        
        if (!handled) {
            System.out.println("EnumChain: No handler found for " + request);
        }
    }
}