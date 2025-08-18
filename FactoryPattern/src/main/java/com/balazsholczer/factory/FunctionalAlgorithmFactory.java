package com.balazsholczer.factory;

import java.util.Map;
import java.util.function.Supplier;

public class FunctionalAlgorithmFactory {
    
    private static final Map<String, Supplier<Algorithm>> ALGORITHMS = Map.of(
        "shortest", ShortestPath::new,
        "spanning", SpanningTree::new
    );
    
    public static Algorithm create(String type) {
        return ALGORITHMS.getOrDefault(type, () -> () -> System.out.println("Unknown algorithm"))
                         .get();
    }
}