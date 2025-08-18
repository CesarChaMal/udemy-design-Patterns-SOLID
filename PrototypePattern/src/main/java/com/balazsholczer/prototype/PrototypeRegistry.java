package com.balazsholczer.prototype;

import java.util.HashMap;
import java.util.Map;

public class PrototypeRegistry {
    
    private final Map<String, Prototype> prototypes = new HashMap<>();
    
    public void registerPrototype(String key, Prototype prototype) {
        prototypes.put(key, prototype);
        System.out.println("Registered prototype: " + key);
    }
    
    public Prototype getPrototype(String key) {
        Prototype prototype = prototypes.get(key);
        if (prototype != null) {
            System.out.println("Cloning prototype: " + key);
            return prototype.clone();
        }
        throw new IllegalArgumentException("No prototype found for key: " + key);
    }
    
    public boolean hasPrototype(String key) {
        return prototypes.containsKey(key);
    }
    
    public void listPrototypes() {
        System.out.println("Available prototypes:");
        prototypes.keySet().forEach(key -> System.out.println("  - " + key));
    }
}