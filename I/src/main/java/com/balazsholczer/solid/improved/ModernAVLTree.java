package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Modern AVL Tree with all segregated interfaces
 */
public class ModernAVLTree<K extends Comparable<K>, V> 
    implements Readable<K, V>, Writable<K, V>, Deletable<K>, Traversable<V>, Balanceable {
    
    private final ConcurrentMap<K, V> data = new ConcurrentHashMap<>();
    private final AtomicInteger height = new AtomicInteger(0);
    
    @Override
    public CompletableFuture<Optional<V>> find(K key) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernAVL: Finding key " + key);
            return Optional.ofNullable(data.get(key));
        });
    }
    
    @Override
    public CompletableFuture<Void> insert(K key, V value) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("ModernAVL: Inserting " + key + " = " + value);
            data.put(key, value);
            updateHeight();
        }).thenCompose(v -> balance());
    }
    
    @Override
    public CompletableFuture<Boolean> delete(K key) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernAVL: Deleting key " + key);
            boolean removed = data.remove(key) != null;
            if (removed) {
                updateHeight();
            }
            return removed;
        }).thenCompose(result -> balance().thenApply(v -> result));
    }
    
    @Override
    public CompletableFuture<Void> balance() {
        return CompletableFuture.runAsync(() -> {
            System.out.println("ModernAVL: Balancing tree");
            // Simplified balancing logic
            updateHeight();
        });
    }
    
    @Override
    public CompletableFuture<Boolean> isBalanced() {
        return CompletableFuture.supplyAsync(() -> {
            // Simplified balance check
            return Math.abs(height.get()) <= 1;
        });
    }
    
    @Override
    public CompletableFuture<Integer> getHeight() {
        return CompletableFuture.completedFuture(height.get());
    }
    
    @Override
    public CompletableFuture<Stream<V>> inOrderTraversal() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernAVL: In-order traversal");
            return data.entrySet().stream()
                      .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                      .map(entry -> entry.getValue());
        });
    }
    
    @Override
    public CompletableFuture<Stream<V>> preOrderTraversal() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernAVL: Pre-order traversal");
            return data.values().stream();
        });
    }
    
    @Override
    public CompletableFuture<Stream<V>> postOrderTraversal() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ModernAVL: Post-order traversal");
            return data.values().stream();
        });
    }
    
    private void updateHeight() {
        height.set((int) Math.log(Math.max(1, data.size())) + 1);
    }
}