package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for write operations
 */
@FunctionalInterface
public interface Writable<K, V> {
    CompletableFuture<Void> insert(K key, V value);
}