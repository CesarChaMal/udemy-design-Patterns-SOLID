package com.balazsholczer.strategy;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class StreamStrategyManager {
    
    private static final Map<String, BinaryOperator<Integer>> STRATEGIES = Map.of(
        "add", Integer::sum,
        "multiply", (a, b) -> a * b,
        "subtract", (a, b) -> a - b,
        "divide", (a, b) -> b != 0 ? a / b : 0
    );
    
    public static int execute(String strategy, int num1, int num2) {
        return STRATEGIES.getOrDefault(strategy, (a, b) -> 0).apply(num1, num2);
    }
    
    public static void executeAll(int num1, int num2) {
        STRATEGIES.entrySet().stream()
            .forEach(entry -> System.out.println(
                entry.getKey() + ": " + entry.getValue().apply(num1, num2)));
    }
    
    public static int executeChain(int initial, String... strategies) {
        return Stream.of(strategies)
            .map(STRATEGIES::get)
            .reduce(initial, (result, strategy) -> strategy.apply(result, result), Integer::sum);
    }
}