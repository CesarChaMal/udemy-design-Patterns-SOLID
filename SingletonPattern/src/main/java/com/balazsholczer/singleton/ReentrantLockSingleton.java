package com.balazsholczer.singleton;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSingleton {
    
    private static volatile ReentrantLockSingleton instance;
    private static final ReentrantLock lock = new ReentrantLock();
    private int counter;
    
    private ReentrantLockSingleton() {}
    
    public static ReentrantLockSingleton getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ReentrantLockSingleton();
                }
            } finally {
                lock.unlock();
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