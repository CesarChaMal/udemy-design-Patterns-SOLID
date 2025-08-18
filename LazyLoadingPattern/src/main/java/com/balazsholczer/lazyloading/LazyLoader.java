package com.balazsholczer.lazyloading;

import java.util.function.Supplier;

public class LazyLoader<T> {
    
    private T value;
    private final Supplier<T> supplier;
    private boolean loaded = false;
    
    public LazyLoader(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    public T get() {
        if (!loaded) {
            System.out.println("LazyLoader: Loading value for the first time");
            value = supplier.get();
            loaded = true;
        } else {
            System.out.println("LazyLoader: Returning cached value");
        }
        return value;
    }
    
    public boolean isLoaded() {
        return loaded;
    }
}