package com.balazsholczer.singleton;

public class RecordSingleton {
    
    public record SingletonData(int counter) {
        public SingletonData withCounter(int newCounter) {
            return new SingletonData(newCounter);
        }
    }
    
    private static volatile SingletonData instance = new SingletonData(0);
    
    public static SingletonData getInstance() {
        return instance;
    }
    
    public static void updateInstance(int counter) {
        synchronized (RecordSingleton.class) {
            instance = instance.withCounter(counter);
        }
    }
    
    public static int getCounter() {
        return instance.counter();
    }
}