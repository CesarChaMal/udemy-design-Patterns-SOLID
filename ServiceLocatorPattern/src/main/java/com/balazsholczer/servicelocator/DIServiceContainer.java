package com.balazsholczer.servicelocator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class DIServiceContainer {
    
    private final Map<Class<?>, Object> singletons = new ConcurrentHashMap<>();
    private final Map<Class<?>, Supplier<?>> factories = new ConcurrentHashMap<>();
    
    public <T> void registerSingleton(Class<T> type, T instance) {
        singletons.put(type, instance);
    }
    
    public <T> void registerFactory(Class<T> type, Supplier<T> factory) {
        factories.put(type, factory);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> type) {
        Object singleton = singletons.get(type);
        if (singleton != null) {
            return (T) singleton;
        }
        
        Supplier<?> factory = factories.get(type);
        if (factory != null) {
            return (T) factory.get();
        }
        
        throw new IllegalArgumentException("No service registered for type: " + type.getName());
    }
    
    public static DIServiceContainer create() {
        DIServiceContainer container = new DIServiceContainer();
        container.registerFactory(DatabaseService.class, DatabaseService::new);
        container.registerFactory(MessagingService.class, MessagingService::new);
        return container;
    }
}