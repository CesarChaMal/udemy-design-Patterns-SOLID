package com.balazsholczer.flyweight;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class RecordFlyweight {
    
    public record FlyweightData(String type, String color) {
        public void render(int x, int y) {
            System.out.println("RecordFlyweight: " + type + " (" + color + ") at (" + x + ", " + y + ")");
        }
    }
    
    public record Context(FlyweightData flyweight, int x, int y) {
        public void render() {
            flyweight.render(x, y);
        }
    }
    
    private static final ConcurrentHashMap<String, FlyweightData> flyweights = new ConcurrentHashMap<>();
    
    public static FlyweightData getFlyweight(String type, String color) {
        String key = type + ":" + color;
        return flyweights.computeIfAbsent(key, k -> {
            System.out.println("Created new record flyweight: " + k);
            return new FlyweightData(type, color);
        });
    }
    
    public static Context createContext(String type, String color, int x, int y) {
        FlyweightData flyweight = getFlyweight(type, color);
        return new Context(flyweight, x, y);
    }
    
    public static void printStats() {
        System.out.println("Record flyweights created: " + flyweights.size());
        flyweights.keySet().forEach(key -> System.out.println("  - " + key));
    }
}