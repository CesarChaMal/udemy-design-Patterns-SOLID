package com.balazsholczer.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
    
    private static final Map<String, Flyweight> flyweights = new HashMap<>();
    
    public static Flyweight getFlyweight(String key) {
        Flyweight flyweight = flyweights.get(key);
        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(key);
            flyweights.put(key, flyweight);
            System.out.println("Created new flyweight for key: " + key);
        } else {
            System.out.println("Reusing existing flyweight for key: " + key);
        }
        return flyweight;
    }
    
    public static int getFlyweightCount() {
        return flyweights.size();
    }
    
    public static void printFlyweights() {
        System.out.println("Total flyweights created: " + flyweights.size());
        flyweights.keySet().forEach(key -> System.out.println("  - " + key));
    }
}