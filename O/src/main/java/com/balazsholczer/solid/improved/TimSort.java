package com.balazsholczer.solid.improved;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Modern TimSort implementation (Java's default sort)
 */
public class TimSort<T extends Comparable<T>> implements SortStrategy<T> {
    
    @Override
    public CompletableFuture<List<T>> sortAsync(List<T> data) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("TimSort: Sorting " + data.size() + " elements using TimSort");
            List<T> result = new ArrayList<>(data);
            Collections.sort(result);
            return result;
        });
    }
}