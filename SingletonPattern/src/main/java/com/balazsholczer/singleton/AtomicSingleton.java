package com.balazsholczer.singleton;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicSingleton {
    
    private static final AtomicReference<AtomicSingleton> INSTANCE = new AtomicReference<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    
    private AtomicSingleton() {}
    
    public static AtomicSingleton getInstance() {
        AtomicSingleton instance = INSTANCE.get();
        if (instance == null) {
            instance = new AtomicSingleton();
            if (!INSTANCE.compareAndSet(null, instance)) {
                instance = INSTANCE.get();
            }
        }
        return instance;
    }
    
    public void setCounter(int counter) {
        this.counter.set(counter);
    }
    
    public int getCounter() {
        return this.counter.get();
    }
    
    public int incrementAndGet() {
        return this.counter.incrementAndGet();
    }
}