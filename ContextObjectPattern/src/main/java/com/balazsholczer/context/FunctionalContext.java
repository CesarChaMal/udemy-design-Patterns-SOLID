package com.balazsholczer.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalContext {
    
    private final Map<String, Object> data = new ConcurrentHashMap<>();
    
    public static FunctionalContext create() {
        System.out.println("FunctionalContext: Created new functional context");
        return new FunctionalContext();
    }
    
    public <T> FunctionalContext with(String key, T value) {
        data.put(key, value);
        System.out.println("FunctionalContext: Added " + key + " = " + value);
        return this;
    }
    
    public <T> T get(String key, Class<T> type) {
        return type.cast(data.get(key));
    }
    
    public <T> T getOrDefault(String key, T defaultValue) {
        return (T) data.getOrDefault(key, defaultValue);
    }
    
    public <T> FunctionalContext compute(String key, Function<Object, T> computation) {
        T result = computation.apply(data.get(key));
        data.put(key, result);
        System.out.println("FunctionalContext: Computed " + key + " = " + result);
        return this;
    }
    
    public <T> FunctionalContext computeIfAbsent(String key, Supplier<T> supplier) {
        data.computeIfAbsent(key, k -> {
            T value = supplier.get();
            System.out.println("FunctionalContext: Computed if absent " + key + " = " + value);
            return value;
        });
        return this;
    }
    
    public FunctionalContext apply(Consumer<FunctionalContext> operation) {
        operation.accept(this);
        return this;
    }
    
    public <R> R transform(Function<FunctionalContext, R> transformation) {
        return transformation.apply(this);
    }
    
    public boolean has(String key) {
        return data.containsKey(key);
    }
    
    public FunctionalContext remove(String key) {
        data.remove(key);
        System.out.println("FunctionalContext: Removed " + key);
        return this;
    }
    
    public FunctionalContext clear() {
        data.clear();
        System.out.println("FunctionalContext: Cleared all data");
        return this;
    }
    
    // Predefined context operations
    public static Consumer<FunctionalContext> authenticate(String user) {
        return ctx -> ctx.with("user", user).with("authenticated", true);
    }
    
    public static Consumer<FunctionalContext> addRole(String role) {
        return ctx -> {
            String roles = ctx.getOrDefault("roles", "");
            ctx.with("roles", roles.isEmpty() ? role : roles + "," + role);
        };
    }
    
    public static Function<FunctionalContext, Boolean> hasRole(String role) {
        return ctx -> {
            String roles = ctx.getOrDefault("roles", "");
            return roles.contains(role);
        };
    }
    
    @Override
    public String toString() {
        return "FunctionalContext{data=" + data + "}";
    }
}