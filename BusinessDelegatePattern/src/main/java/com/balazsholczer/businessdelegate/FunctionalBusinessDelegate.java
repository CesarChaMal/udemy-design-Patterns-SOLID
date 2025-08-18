package com.balazsholczer.businessdelegate;

import java.util.Map;
import java.util.function.Function;

public class FunctionalBusinessDelegate {
    
    private final Map<String, Function<String, String>> services;
    
    public FunctionalBusinessDelegate() {
        this.services = Map.of(
            "order", request -> {
                System.out.println("FunctionalDelegate: Processing order - " + request);
                return "Order processed: " + request;
            },
            "payment", request -> {
                System.out.println("FunctionalDelegate: Processing payment - " + request);
                return "Payment processed: " + request;
            }
        );
    }
    
    public String processRequest(String serviceType, String request) {
        Function<String, String> service = services.get(serviceType.toLowerCase());
        if (service == null) {
            throw new IllegalArgumentException("Unknown service: " + serviceType);
        }
        return service.apply(request);
    }
}