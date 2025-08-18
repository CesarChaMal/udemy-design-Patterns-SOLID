package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * Modern BST implementation with segregated interfaces
 */
public class ModernBinarySearchTree<K extends Comparable<K>, V> 
    implements Readable<K, V>, Writable<K, V>, Deletable<K>, Traversable<V> {
    
    private final ConcurrentMap<K, V> data = new ConcurrentHashMap<>();
    
    @Override
    public CompletableFuture<Optional<V>> find(K key) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernBST: Finding key " + key);
            return Optional.ofNullable(data.get(key));
        });
    }
    
    @Override
    public CompletableFuture<Void> insert(K key, V value) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("ModernBST: Inserting " + key + " = " + value);
            data.put(key, value);
        });
    }
    
    @Override
    public CompletableFuture<Boolean> delete(K key) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernBST: Deleting key " + key);
            return data.remove(key) != null;
        });
    }
    
    @Override
    public CompletableFuture<Stream<V>> inOrderTraversal() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernBST: In-order traversal");
            return data.entrySet().stream()
                      .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                      .map(entry -> entry.getValue());
        });
    }
    
    @Override
    public CompletableFuture<Stream<V>> preOrderTraversal() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernBST: Pre-order traversal");
            return data.values().stream();
        });
    }
    
    @Override
    public CompletableFuture<Stream<V>> postOrderTraversal() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernBST: Post-order traversal");
            return data.values().stream();
        });
    }
}