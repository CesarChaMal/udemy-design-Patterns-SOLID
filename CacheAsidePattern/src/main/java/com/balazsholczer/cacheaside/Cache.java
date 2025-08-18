package com.balazsholczer.cacheaside;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Cache<K, V> {
    private final Map<K, V> cache = new HashMap<>();
    private final String name;
    
    public Cache(String name) {
        this.name = name;
    }
    
    public Optional<V> get(K key) {
        V value = cache.get(key);
        if (value != null) {
            System.out.println("Cache: HIT for key " + key + " in " + name);
            return Optional.of(value);
        } else {
            System.out.println("Cache: MISS for key " + key + " in " + name);
            return Optional.empty();
        }
    }
    
    public void put(K key, V value) {
        cache.put(key, value);
        System.out.println("Cache: PUT key " + key + " in " + name);
    }
    
    public void evict(K key) {
        cache.remove(key);
        System.out.println("Cache: EVICTED key " + key + " from " + name);
    }
    
    public void clear() {
        cache.clear();
        System.out.println("Cache: CLEARED " + name);
    }
    
    public int size() {
        return cache.size();
    }
}