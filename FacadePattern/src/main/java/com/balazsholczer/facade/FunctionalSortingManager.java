package com.balazsholczer.facade;

import java.util.Map;
import java.util.function.Supplier;

public class FunctionalSortingManager {
    
    private static final Map<String, Runnable> ALGORITHMS = Map.of(
        "bubble", () -> System.out.println("Bubbesort..."),
        "merge", () -> System.out.println("Mergesort..."),
        "heap", () -> System.out.println("Heapsort...")
    );
    
    public static void sort(String algorithm) {
        ALGORITHMS.getOrDefault(algorithm, () -> System.out.println("Unknown algorithm")).run();
    }
    
    public static void sortAll() {
        ALGORITHMS.values().forEach(Runnable::run);
    }
}