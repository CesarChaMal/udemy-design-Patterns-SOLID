package com.balazsholczer.wsbroker;

import java.util.HashMap;
import java.util.Map;

public class WebServiceBroker {
    
    private final Map<String, WebService> services = new HashMap<>();
    
    public void registerService(String serviceName, WebService service) {
        services.put(serviceName, service);
        System.out.println("WebServiceBroker: Registered " + serviceName);
    }
    
    public String invokeService(String serviceName, String request) {
        System.out.println("WebServiceBroker: Invoking " + serviceName);
        
        WebService service = services.get(serviceName);
        if (service != null) {
            try {
                String response = service.invoke(request);
                System.out.println("WebServiceBroker: Service call successful");
                return response;
            } catch (Exception e) {
                System.out.println("WebServiceBroker: Service call failed - " + e.getMessage());
                return "{\"error\": \"Service unavailable\"}";
            }
        }
        
        System.out.println("WebServiceBroker: Service not found - " + serviceName);
        return "{\"error\": \"Service not found\"}";
    }
    
    public boolean isServiceAvailable(String serviceName) {
        return services.containsKey(serviceName);
    }
}