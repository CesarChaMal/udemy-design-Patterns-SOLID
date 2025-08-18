package com.balazsholczer.factory;

import java.util.function.Supplier;

public enum EnumAlgorithmFactory {
    SHORTEST_PATH(ShortestPath::new),
    SPANNING_TREE(SpanningTree::new);
    
    private final Supplier<Algorithm> factory;
    
    EnumAlgorithmFactory(Supplier<Algorithm> factory) {
        this.factory = factory;
    }
    
    public Algorithm create() {
        return factory.get();
    }
    
    public static Algorithm create(String type) {
        try {
            return valueOf(type.toUpperCase()).create();
        } catch (IllegalArgumentException e) {
            return () -> System.out.println("Unknown algorithm: " + type);
        }
    }
}