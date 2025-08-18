package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for delete operations
 */
@FunctionalInterface
public interface Deletable<K> {
    CompletableFuture<Boolean> delete(K key);
}