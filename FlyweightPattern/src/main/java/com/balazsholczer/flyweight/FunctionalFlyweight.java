package com.balazsholczer.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class FunctionalFlyweight {
    
    public record IntrinsicState(String type, String style) {}
    public record ExtrinsicState(int x, int y, String color) {}
    public record RenderCommand(IntrinsicState intrinsic, ExtrinsicState extrinsic) {}
    
    private static final Map<IntrinsicState, Function<ExtrinsicState, String>> flyweights = new ConcurrentHashMap<>();
    
    public static Function<ExtrinsicState, String> getFlyweight(IntrinsicState intrinsic) {
        return flyweights.computeIfAbsent(intrinsic, key -> {
            System.out.println("Created functional flyweight: " + key);
            return extrinsic -> "FunctionalFlyweight: " + key.type() + " (" + key.style() + ") " +
                               "at (" + extrinsic.x() + ", " + extrinsic.y() + ") color=" + extrinsic.color();
        });
    }
    
    public static String render(IntrinsicState intrinsic, ExtrinsicState extrinsic) {
        Function<ExtrinsicState, String> flyweight = getFlyweight(intrinsic);
        return flyweight.apply(extrinsic);
    }
    
    public static String render(RenderCommand command) {
        return render(command.intrinsic(), command.extrinsic());
    }
    
    public static void printStats() {
        System.out.println("Functional flyweights created: " + flyweights.size());
        flyweights.keySet().forEach(key -> System.out.println("  - " + key));
    }
    
    // Predefined flyweight creators
    public static final Function<String, IntrinsicState> CIRCLE_CREATOR = 
        style -> new IntrinsicState("Circle", style);
    
    public static final Function<String, IntrinsicState> SQUARE_CREATOR = 
        style -> new IntrinsicState("Square", style);
}