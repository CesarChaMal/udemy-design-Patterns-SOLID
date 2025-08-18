package com.balazsholczer.apigateway;

import java.util.HashMap;
import java.util.Map;

public class APIGateway {
    
    private final Map<String, MicroService> services = new HashMap<>();
    
    public void registerService(String pathPrefix, MicroService service) {
        services.put(pathPrefix, service);
        System.out.println("APIGateway: Registered " + service.getServiceName() + " for " + pathPrefix);
    }
    
    public Response routeRequest(Request request) {
        System.out.println("APIGateway: Routing request " + request);
        
        // Authentication
        if (!authenticate(request)) {
            return new Response(401, "{\"error\": \"Unauthorized\"}");
        }
        
        // Rate limiting
        if (!checkRateLimit(request)) {
            return new Response(429, "{\"error\": \"Rate limit exceeded\"}");
        }
        
        // Route to appropriate service
        MicroService service = findService(request.getPath());
        if (service != null) {
            Response response = service.handleRequest(request);
            
            // Add common headers
            response.addHeader("X-Gateway", "APIGateway");
            response.addHeader("X-Service", service.getServiceName());
            
            return response;
        }
        
        return new Response(404, "{\"error\": \"Service not found\"}");
    }
    
    private boolean authenticate(Request request) {
        String authHeader = request.getHeaders().get("Authorization");
        boolean authenticated = authHeader != null && authHeader.startsWith("Bearer ");
        System.out.println("APIGateway: Authentication " + (authenticated ? "passed" : "failed"));
        return authenticated;
    }
    
    private boolean checkRateLimit(Request request) {
        // Simplified rate limiting
        System.out.println("APIGateway: Rate limit check passed");
        return true;
    }
    
    private MicroService findService(String path) {
        for (Map.Entry<String, MicroService> entry : services.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}