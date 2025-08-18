package com.balazsholczer.proxy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class CachingProxy {
    
    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    
    public static class CachedImageService {
        private final Function<String, String> imageLoader;
        
        public CachedImageService() {
            this.imageLoader = filename -> {
                System.out.println("Loading image: " + filename);
                return "Image data for: " + filename;
            };
        }
        
        public void display(String filename) {
            String imageData = cache.computeIfAbsent(filename, imageLoader);
            System.out.println("Displaying: " + imageData);
        }
        
        public void clearCache() {
            cache.clear();
        }
    }
}