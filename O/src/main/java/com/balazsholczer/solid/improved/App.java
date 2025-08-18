package com.balazsholczer.solid.improved;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Modern Open/Closed Principle Implementation
 * 
 * Improvements:
 * - Functional interfaces with lambdas
 * - Async operations with CompletableFuture
 * - Parallel processing capabilities
 * - Stream API integration
 * - Generic type safety
 * - Strategy pattern for extensibility
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Modern Open/Closed Principle ===");
        System.out.println("Improved with functional programming and async operations");
        System.out.println();
        
        List<Integer> data = Arrays.asList(64, 34, 25, 12, 22, 11, 90, 5, 77, 30);
        System.out.println("Original data: " + data);
        
        // Test different sorting strategies
        SortContext<Integer> context = new SortContext<>(new ParallelMergeSort<Integer>());
        
        try {
            // Parallel merge sort
            System.out.println("\n--- Parallel Merge Sort ---");
            List<Integer> result1 = context.executeSort(data).join();
            System.out.println("Sorted: " + result1);
            
            // Switch to stream sort
            System.out.println("\n--- Stream Sort ---");
            context.setStrategy(new StreamSort<Integer>());
            List<Integer> result2 = context.executeSort(data).join();
            System.out.println("Sorted: " + result2);
            
            // Switch to TimSort
            System.out.println("\n--- TimSort ---");
            context.setStrategy(new TimSort<Integer>());
            List<Integer> result3 = context.executeSort(data).join();
            System.out.println("Sorted: " + result3);
            
            // Demonstrate extensibility with lambda
            System.out.println("\n--- Custom Lambda Sort ---");
            SortStrategy<Integer> customSort = (list) -> 
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("CustomSort: Reverse sorting with lambda");
                    return list.stream().sorted((a, b) -> b.compareTo(a)).toList();
                });
            
            context.setStrategy(customSort);
            List<Integer> result4 = context.executeSort(data).join();
            System.out.println("Reverse sorted: " + result4);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Modern OCP Benefits ===");
        System.out.println("✅ Functional interfaces with lambdas");
        System.out.println("✅ Async operations with CompletableFuture");
        System.out.println("✅ Parallel processing capabilities");
        System.out.println("✅ Stream API integration");
        System.out.println("✅ Easy extension without modification");
    }
}