package com.balazsholczer.factory;

import java.util.Map;

public class LambdaAlgorithmFactory {
    
    private static final Map<String, Algorithm> ALGORITHMS = Map.of(
        "shortest", () -> System.out.println("Solving the shortest path problem..."),
        "spanning", () -> System.out.println("Solving the spanning tree problem...")
    );
    
    public static Algorithm create(String type) {
        return ALGORITHMS.getOrDefault(type, () -> System.out.println("Unknown algorithm: " + type));
    }
    
    public static Algorithm createComposite(String... types) {
        return () -> {
            for (String type : types) {
                create(type).solve();
            }
        };
    }
}