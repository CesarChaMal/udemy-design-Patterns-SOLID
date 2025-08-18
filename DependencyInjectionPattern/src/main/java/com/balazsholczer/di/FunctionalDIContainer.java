package com.balazsholczer.di;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalDIContainer {

    private final Map<String, Object> singletons = new ConcurrentHashMap<>();
    private final Map<String, Supplier<Object>> providers = new ConcurrentHashMap<>();

    public <T> void register(String name, Supplier<T> provider) {
        providers.put(name, () -> provider.get());
        System.out.println("FunctionalDI: Registered provider - " + name);
    }

    public <T> void registerSingleton(String name, T instance) {
        singletons.put(name, instance);
        System.out.println("FunctionalDI: Registered singleton - " + name);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        Object instance = singletons.get(name);
        if (instance != null) {
            return (T) instance;
        }
        Supplier<Object> provider = providers.get(name);
        if (provider != null) {
            return (T) provider.get();
        }
        throw new IllegalArgumentException("No registration found: " + name);
    }

    // Added for test compatibility
    public Object resolve(String name) {
        return get(name);
    }

    public <T> T resolve(String name, Class<T> type) {
        Object obj = get(name);
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException("Object for '" + name + "' not of type " + type.getName());
        }
        return type.cast(obj);
    }

    public <T, R> Function<T, R> inject(String dependencyName, Function<T, Function<Object, R>> factory) {
        return input -> {
            Object dependency = get(dependencyName);
            return factory.apply(input).apply(dependency);
        };
    }
}