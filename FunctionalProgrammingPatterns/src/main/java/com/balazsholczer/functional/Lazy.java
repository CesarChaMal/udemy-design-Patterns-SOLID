package com.balazsholczer.functional;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Lazy Evaluation Pattern - deferred computation
 */
public class Lazy<T> {
    private final Supplier<T> supplier;
    private volatile T value;
    private volatile boolean computed = false;
    
    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }
    
    public T get() {
        if (!computed) {
            synchronized (this) {
                if (!computed) {
                    System.out.println("Lazy: Computing value...");
                    value = supplier.get();
                    computed = true;
                }
            }
        }
        return value;
    }
    
    public <U> Lazy<U> map(Function<T, U> mapper) {
        return Lazy.of(() -> mapper.apply(get()));
    }
    
    public <U> Lazy<U> flatMap(Function<T, Lazy<U>> mapper) {
        return Lazy.of(() -> mapper.apply(get()).get());
    }
    
    public boolean isComputed() {
        return computed;
    }
    
    @Override
    public String toString() {
        return computed ? "Lazy(" + value + ")" : "Lazy(?)";
    }
}