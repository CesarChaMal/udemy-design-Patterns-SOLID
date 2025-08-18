package com.balazsholczer.functional;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Closure Pattern - functions that capture and retain access to variables from their lexical scope
 * 
 * A closure is a function that has access to variables in its outer (enclosing) scope 
 * even after the outer function has returned. This allows for:
 * - Data encapsulation
 * - Factory functions
 * - Partial application
 * - Stateful functions
 */
public class Closure {
    
    /**
     * Creates a counter closure that maintains internal state
     */
    public static Supplier<Integer> createCounter(int initialValue) {
        // The counter array is captured by the closure
        int[] counter = {initialValue};
        
        return () -> {
            counter[0]++;
            return counter[0];
        };
    }
    
    /**
     * Creates a multiplier closure that captures the factor
     */
    public static Function<Integer, Integer> createMultiplier(int factor) {
        // The factor is captured by the closure
        return x -> x * factor;
    }
    
    /**
     * Creates an accumulator closure that maintains running sum
     */
    public static Function<Integer, Integer> createAccumulator(int initialSum) {
        // The sum array is captured by the closure
        int[] sum = {initialSum};
        
        return value -> {
            sum[0] += value;
            return sum[0];
        };
    }
    
    /**
     * Creates a configuration-based processor closure
     */
    public static Function<String, String> createProcessor(String prefix, String suffix, boolean uppercase) {
        // Configuration parameters are captured by the closure
        return input -> {
            String processed = input;
            if (uppercase) {
                processed = processed.toUpperCase();
            }
            return prefix + processed + suffix;
        };
    }
    
    /**
     * Creates a memoized function closure that caches results
     */
    public static <T, R> Function<T, R> createMemoized(Function<T, R> function) {
        // The cache is captured by the closure
        java.util.Map<T, R> cache = new java.util.concurrent.ConcurrentHashMap<>();
        
        return input -> cache.computeIfAbsent(input, function);
    }
    
    /**
     * Creates a rate-limited function closure
     */
    public static <T, R> Function<T, R> createRateLimited(Function<T, R> function, long delayMs) {
        // The last execution time is captured by the closure
        long[] lastExecution = {0};
        
        return input -> {
            long now = System.currentTimeMillis();
            if (now - lastExecution[0] < delayMs) {
                throw new RuntimeException("Rate limit exceeded");
            }
            lastExecution[0] = now;
            return function.apply(input);
        };
    }
    
    /**
     * Creates a retry closure that captures retry configuration
     */
    public static <T, R> Function<T, R> createRetryable(Function<T, R> function, int maxRetries) {
        // Retry configuration is captured by the closure
        return input -> {
            RuntimeException lastException = null;
            
            for (int attempt = 0; attempt <= maxRetries; attempt++) {
                try {
                    return function.apply(input);
                } catch (RuntimeException e) {
                    lastException = e;
                    if (attempt == maxRetries) {
                        break;
                    }
                    // Simple backoff
                    try {
                        Thread.sleep(100 * (attempt + 1));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted during retry", ie);
                    }
                }
            }
            
            throw new RuntimeException("Function failed after " + (maxRetries + 1) + " attempts", lastException);
        };
    }
    
    /**
     * Creates a function composition closure
     */
    public static <A, B, C> Function<A, C> compose(Function<B, C> f, Function<A, B> g) {
        // Both functions are captured by the closure
        return a -> f.apply(g.apply(a));
    }
    
    /**
     * Creates a curried function closure
     */
    public static <A, B, C> Function<A, Function<B, C>> curry(java.util.function.BiFunction<A, B, C> function) {
        // The original function is captured by the closure
        return a -> b -> function.apply(a, b);
    }
    
    /**
     * Creates a partial application closure
     */
    public static <A, B, C> Function<B, C> partial(java.util.function.BiFunction<A, B, C> function, A a) {
        // The function and first argument are captured by the closure
        return b -> function.apply(a, b);
    }
    
    /**
     * Demonstrates closure behavior with lexical scoping
     */
    public static class ClosureDemo {
        private final String name;
        
        public ClosureDemo(String name) {
            this.name = name;
        }
        
        /**
         * Creates a greeting closure that captures the instance's name
         */
        public Function<String, String> createGreeter() {
            // The 'name' field is captured by the closure
            return message -> "Hello " + message + ", I'm " + name;
        }
        
        /**
         * Creates a method reference closure
         */
        public Supplier<String> createNameSupplier() {
            // The instance method reference captures 'this'
            return this::getName;
        }
        
        private String getName() {
            return name;
        }
    }
}