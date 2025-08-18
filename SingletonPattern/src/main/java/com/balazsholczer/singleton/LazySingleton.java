package com.balazsholczer.singleton;

public class LazySingleton {
    
    private static volatile LazySingleton instance;
    private int counter;
    
    private LazySingleton() {}
    
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
    
    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public int getCounter() {
        return this.counter;
    }
}