package com.balazsholczer.solid.improved;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Modern stream-based sorting implementation
 */
public class StreamSort<T extends Comparable<T>> implements SortStrategy<T> {
    
    @Override
    public CompletableFuture<List<T>> sortAsync(List<T> data) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("StreamSort: Sorting " + data.size() + " elements using streams");
            return data.parallelStream()
                      .sorted()
                      .collect(Collectors.toList());
        });
    }
}