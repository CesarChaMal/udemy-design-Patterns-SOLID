package com.balazsholczer.facade;

import java.util.List;
import java.util.stream.Stream;

public class StreamSortingManager {
    
    private static final List<Runnable> ALGORITHMS = List.of(
        () -> System.out.println("Bubbesort..."),
        () -> System.out.println("Mergesort..."),
        () -> System.out.println("Heapsort...")
    );
    
    public static void sortAll() {
        ALGORITHMS.forEach(Runnable::run);
    }
    
    public static void sortParallel() {
        ALGORITHMS.parallelStream().forEach(Runnable::run);
    }
    
    public static void sortByIndex(int... indices) {
        java.util.Arrays.stream(indices)
            .filter(i -> i >= 0 && i < ALGORITHMS.size())
            .mapToObj(ALGORITHMS::get)
            .forEach(Runnable::run);
    }
}