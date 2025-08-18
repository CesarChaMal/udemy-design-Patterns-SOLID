package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for read operations
 */
@FunctionalInterface
public interface Readable<K, V> {
    CompletableFuture<Optional<V>> find(K key);
}