package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Modern Redis repository implementation for caching
 */
public class RedisRepository<T> implements DatabaseRepository<T> {
    private final ConcurrentMap<String, T> storage = new ConcurrentHashMap<>();
    
    @Override
    public CompletableFuture<Void> save(String id, T entity) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("RedisRepository: Caching entity " + id + " in memory with TTL");
            storage.put(id, entity);
        });
    }
    
    @Override
    public CompletableFuture<Optional<T>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("RedisRepository: Fast cache lookup for " + id);
            return Optional.ofNullable(storage.get(id));
        });
    }
    
    @Override
    public CompletableFuture<Boolean> delete(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("RedisRepository: Evicting cache entry " + id);
            return storage.remove(id) != null;
        });
    }
    
    @Override
    public CompletableFuture<Boolean> exists(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("RedisRepository: Fast cache existence check " + id);
            return storage.containsKey(id);
        });
    }
    
    @Override
    public String getRepositoryType() {
        return "Redis";
    }
}