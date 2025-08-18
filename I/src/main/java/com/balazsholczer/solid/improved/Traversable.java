package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Interface for tree traversal operations
 */
public interface Traversable<V> {
    CompletableFuture<Stream<V>> inOrderTraversal();
    CompletableFuture<Stream<V>> preOrderTraversal();
    CompletableFuture<Stream<V>> postOrderTraversal();
}