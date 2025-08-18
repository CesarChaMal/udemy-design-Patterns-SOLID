package com.balazsholczer.solid.improved;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Modern context for sorting operations with strategy pattern
 */
public class SortContext<T extends Comparable<T>> {
    private SortStrategy<T> strategy;
    
    public SortContext(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }
    
    public CompletableFuture<List<T>> executeSort(List<T> data) {
        System.out.println("SortContext: Using " + strategy.getName() + " strategy");
        return strategy.sortAsync(data);
    }
    
    public SortStrategy<T> getStrategy() {
        return strategy;
    }
}