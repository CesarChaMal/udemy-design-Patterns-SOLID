package com.balazsholczer.solid.improved;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Modern functional sorting strategy interface
 */
@FunctionalInterface
public interface SortStrategy<T extends Comparable<T>> {
    CompletableFuture<List<T>> sortAsync(List<T> data);
    
    default String getName() {
        return this.getClass().getSimpleName();
    }
}