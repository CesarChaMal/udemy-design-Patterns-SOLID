package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Modern MongoDB repository implementation
 */
public class MongoDBRepository<T> implements DatabaseRepository<T> {
    private final ConcurrentMap<String, T> storage = new ConcurrentHashMap<>();
    
    @Override
    public CompletableFuture<Void> save(String id, T entity) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("MongoDBRepository: Saving document " + id + " with flexible schema");
            storage.put(id, entity);
        });
    }
    
    @Override
    public CompletableFuture<Optional<T>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("MongoDBRepository: Finding document " + id + " with NoSQL query");
            return Optional.ofNullable(storage.get(id));
        });
    }
    
    @Override
    public CompletableFuture<Boolean> delete(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("MongoDBRepository: Deleting document " + id);
            return storage.remove(id) != null;
        });
    }
    
    @Override
    public CompletableFuture<Boolean> exists(String id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("MongoDBRepository: Checking document existence " + id);
            return storage.containsKey(id);
        });
    }
    
    @Override
    public String getRepositoryType() {
        return "MongoDB";
    }
}