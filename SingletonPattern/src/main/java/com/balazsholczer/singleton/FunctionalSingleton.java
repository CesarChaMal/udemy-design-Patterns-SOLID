package com.balazsholczer.singleton;

import java.util.function.Supplier;

public class FunctionalSingleton {
    
    private static final Supplier<FunctionalSingleton> INSTANCE = 
        new Supplier<FunctionalSingleton>() {
            private volatile FunctionalSingleton instance;
            
            @Override
            public FunctionalSingleton get() {
                if (instance == null) {
                    synchronized (this) {
                        if (instance == null) {
                            instance = new FunctionalSingleton();
                        }
                    }
                }
                return instance;
            }
        };
    
    private int counter;
    
    private FunctionalSingleton() {}
    
    public static FunctionalSingleton getInstance() {
        return INSTANCE.get();
    }
    
    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public int getCounter() {
        return this.counter;
    }
}