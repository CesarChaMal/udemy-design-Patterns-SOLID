package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Modern database repository interface with reactive operations
 */
public interface DatabaseRepository<T> {
    CompletableFuture<Void> save(String id, T entity);
    CompletableFuture<Optional<T>> findById(String id);
    CompletableFuture<Boolean> delete(String id);
    CompletableFuture<Boolean> exists(String id);
    String getRepositoryType();
}