package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Modern PostgreSQL repository implementation
 */
public class PostgreSQLRepository<T> implements DatabaseRepository<T> {
    private final ConcurrentMap<String, T> storage = new ConcurrentHashMap<>();
    
    @Override
    public CompletableFuture<Void> save(String id, T entity) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("PostgreSQLRepository: Saving entity " + id + " with ACID transactions");
            storage.put(id, entity);
        });
    }
    
    @Override
    public CompletableFuture<Optional<T>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("PostgreSQLRepository: Finding entity " + id + " with SQL query");
            return Optional.ofNullable(storage.get(id));
        });
    }
    
    @Override
    public CompletableFuture<Boolean> delete(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("PostgreSQLRepository: Deleting entity " + id + " with CASCADE");
            return storage.remove(id) != null;
        });
    }
    
    @Override
    public CompletableFuture<Boolean> exists(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("PostgreSQLRepository: Checking existence of " + id);
            return storage.containsKey(id);
        });
    }
    
    @Override
    public String getRepositoryType() {
        return "PostgreSQL";
    }
}