package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Continuation Pattern - represents "what to do next"
 */
@FunctionalInterface
public interface Continuation<T, R> {
    R apply(T value);
    
    default <U> Continuation<T, U> map(Function<R, U> mapper) {
        return value -> mapper.apply(apply(value));
    }
    
    default <U> Continuation<T, U> flatMap(Function<R, Continuation<T, U>> mapper) {
        return value -> mapper.apply(apply(value)).apply(value);
    }
    
    static <T> Continuation<T, T> identity() {
        return value -> value;
    }
    
    // Continuation-passing style factorial
    static int factorialCPS(int n) {
        return factorialCPS(n, x -> x);
    }
    
    private static int factorialCPS(int n, Continuation<Integer, Integer> cont) {
        if (n <= 1) {
            return cont.apply(1);
        }
        return factorialCPS(n - 1, result -> cont.apply(n * result));
    }
}