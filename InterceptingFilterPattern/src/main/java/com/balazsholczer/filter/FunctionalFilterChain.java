package com.balazsholczer.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class FunctionalFilterChain {
    
    private final List<BiConsumer<Request, Response>> filters = new ArrayList<>();
    
    public void addFilter(BiConsumer<Request, Response> filter) {
        filters.add(filter);
        System.out.println("FunctionalFilterChain: Added functional filter");
    }
    
    public void process(Request request, Response response) {
        System.out.println("FunctionalFilterChain: Processing with " + filters.size() + " filters");
        
        // Apply all filters in sequence
        for (BiConsumer<Request, Response> filter : filters) {
            filter.accept(request, response);
            
            // Stop processing if response indicates error
            if (response.getStatusCode() >= 400) {
                break;
            }
        }
        
        // Process actual request if no errors
        if (response.getStatusCode() < 400) {
            response.setContent("Functionally processed: " + request.getUri());
        }
    }
    
    // Predefined functional filters
    public static BiConsumer<Request, Response> authFilter() {
        return (req, res) -> {
            System.out.println("Functional: Authentication check");
            if (req.getHeader("Authorization") == null) {
                res.setStatusCode(401);
                res.setContent("Unauthorized");
            }
        };
    }
    
    public static BiConsumer<Request, Response> loggingFilter() {
        return (req, res) -> {
            System.out.println("Functional: Logging - " + req);
        };
    }
    
    public static BiConsumer<Request, Response> corsFilter() {
        return (req, res) -> {
            System.out.println("Functional: Adding CORS headers");
            res.setHeader("Access-Control-Allow-Origin", "*");
        };
    }
}