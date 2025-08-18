package com.balazsholczer.functional;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Higher-Order Functions Pattern - functions that operate on other functions
 */
public class HigherOrderFunctions {
    
    // Function composition
    public static <A, B, C> Function<A, C> compose(Function<B, C> f, Function<A, B> g) {
        return a -> f.apply(g.apply(a));
    }
    
    // Currying - transforms multi-argument function into chain of single-argument functions
    public static <A, B, C> Function<A, Function<B, C>> curry(Function2<A, B, C> function) {
        return a -> b -> function.apply(a, b);
    }
    
    // Partial application
    public static <A, B, C> Function<B, C> partial(Function2<A, B, C> function, A a) {
        return b -> function.apply(a, b);
    }
    
    // Memoization - caches function results
    public static <T, R> Function<T, R> memoize(Function<T, R> function) {
        return new Function<T, R>() {
            private final java.util.Map<T, R> cache = new java.util.concurrent.ConcurrentHashMap<>();
            
            @Override
            public R apply(T input) {
                return cache.computeIfAbsent(input, function);
            }
        };
    }
    
    // Function that returns a function (closure)
    public static UnaryOperator<Integer> createMultiplier(int factor) {
        return x -> x * factor;
    }
    
    // Predicate combinators
    public static <T> Predicate<T> and(Predicate<T> p1, Predicate<T> p2) {
        return t -> p1.test(t) && p2.test(t);
    }
    
    public static <T> Predicate<T> or(Predicate<T> p1, Predicate<T> p2) {
        return t -> p1.test(t) || p2.test(t);
    }
    
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
    
    // Uncurrying - transforms curried function back to multi-argument function
    public static <A, B, C> Function2<A, B, C> uncurry(Function<A, Function<B, C>> curried) {
        return (a, b) -> curried.apply(a).apply(b);
    }
    
    // Flip - swaps arguments of a two-argument function
    public static <A, B, C> Function2<B, A, C> flip(Function2<A, B, C> function) {
        return (b, a) -> function.apply(a, b);
    }
    
    // Identity function
    public static <T> Function<T, T> identity() {
        return t -> t;
    }
    
    // Constant function
    public static <T, R> Function<T, R> constant(R value) {
        return t -> value;
    }
    
    // Function pipeline (left-to-right composition)
    @SafeVarargs
    public static <T> Function<T, T> pipe(Function<T, T>... functions) {
        return input -> {
            T result = input;
            for (Function<T, T> function : functions) {
                result = function.apply(result);
            }
            return result;
        };
    }
    
    @FunctionalInterface
    public interface Function2<A, B, C> {
        C apply(A a, B b);
    }
}