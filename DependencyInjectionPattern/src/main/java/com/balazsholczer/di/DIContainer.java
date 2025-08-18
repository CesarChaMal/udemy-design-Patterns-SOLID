package com.balazsholczer.di;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DIContainer {
    
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Supplier<?>> factories = new HashMap<>();
    
    // Register singleton instance
    public <T> void registerSingleton(Class<T> type, T instance) {
        singletons.put(type, instance);
        System.out.println("DIContainer: Registered singleton - " + type.getSimpleName());
    }
    
    // Register factory for creating instances
    public <T> void registerFactory(Class<T> type, Supplier<T> factory) {
        factories.put(type, factory);
        System.out.println("DIContainer: Registered factory - " + type.getSimpleName());
    }
    
    // Get instance from container
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type) {
        // Check for singleton first
        Object singleton = singletons.get(type);
        if (singleton != null) {
            System.out.println("DIContainer: Returning singleton - " + type.getSimpleName());
            return (T) singleton;
        }
        
        // Check for factory
        Supplier<?> factory = factories.get(type);
        if (factory != null) {
            System.out.println("DIContainer: Creating instance via factory - " + type.getSimpleName());
            return (T) factory.get();
        }
        
        throw new IllegalArgumentException("No registration found for type: " + type.getName());
    }
    
    // Auto-wire dependencies (simple implementation)
    public <T> T createInstance(Class<T> type) {
        try {
            if (type == UserService.class) {
                UserRepository repository = getInstance(UserRepository.class);
                return (T) new UserService(repository);
            }
            
            // Default constructor
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type.getName(), e);
        }
    }
}