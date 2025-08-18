package com.balazsholczer.solid.improved;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * Modern parallel merge sort implementation
 */
public class ParallelMergeSort<T extends Comparable<T>> implements SortStrategy<T> {
    
    @Override
    public CompletableFuture<List<T>> sortAsync(List<T> data) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("ParallelMergeSort: Sorting " + data.size() + " elements");
            return parallelMergeSort(new ArrayList<>(data));
        }, ForkJoinPool.commonPool());
    }
    
    private List<T> parallelMergeSort(List<T> list) {
        if (list.size() <= 1) return list;
        
        int mid = list.size() / 2;
        List<T> left = list.subList(0, mid);
        List<T> right = list.subList(mid, list.size());
        
        // Parallel divide and conquer
        CompletableFuture<List<T>> leftFuture = CompletableFuture.supplyAsync(() -> parallelMergeSort(left));
        CompletableFuture<List<T>> rightFuture = CompletableFuture.supplyAsync(() -> parallelMergeSort(right));
        
        return leftFuture.thenCombine(rightFuture, this::merge).join();
    }
    
    private List<T> merge(List<T> left, List<T> right) {
        List<T> result = new ArrayList<>();
        int i = 0, j = 0;
        
        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));
        
        return result;
    }
}