package com.balazsholczer.servicelocator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class FunctionalServiceLocator {
    
    private static final Map<String, Supplier<Service>> SERVICE_REGISTRY = Map.of(
        "databaseService", DatabaseService::new,
        "messagingService", MessagingService::new
    );
    
    private static final Map<String, Service> CACHE = new ConcurrentHashMap<>();
    
    public static Service getService(String name) {
        return CACHE.computeIfAbsent(name, key -> 
            SERVICE_REGISTRY.getOrDefault(key, () -> new Service() {
                @Override
                public String getName() {
                    return "unknown";
                }
                
                @Override
                public void execute() {
                    System.out.println("Unknown service: " + key);
                }
            }).get()
        );
    }
    
    public static void clearCache() {
        CACHE.clear();
    }
}