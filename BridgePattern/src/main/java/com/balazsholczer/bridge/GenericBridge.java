package com.balazsholczer.bridge;

import java.util.Map;
import java.util.function.Function;

public class GenericBridge<T, R> {
    
    private final Map<String, Function<T, R>> implementations;
    
    public GenericBridge(Map<String, Function<T, R>> implementations) {
        this.implementations = Map.copyOf(implementations);
    }
    
    public R execute(String implementationType, T data) {
        Function<T, R> implementation = implementations.get(implementationType);
        if (implementation == null) {
            throw new IllegalArgumentException("No implementation for type: " + implementationType);
        }
        return implementation.apply(data);
    }
    
    public record DrawCommand(String shape, double x, double y, double width, double height) {}
    
    public static GenericBridge<DrawCommand, Void> createDrawingBridge() {
        return new GenericBridge<>(Map.of(
            "opengl", command -> {
                if ("circle".equals(command.shape())) {
                    System.out.println("OpenGL: Drawing circle at (" + command.x() + ", " + command.y() + ") with radius " + command.width());
                } else {
                    System.out.println("OpenGL: Drawing rectangle at (" + command.x() + ", " + command.y() + ") with size " + command.width() + "x" + command.height());
                }
                return null;
            },
            "directx", command -> {
                if ("circle".equals(command.shape())) {
                    System.out.println("DirectX: Drawing circle at (" + command.x() + ", " + command.y() + ") with radius " + command.width());
                } else {
                    System.out.println("DirectX: Drawing rectangle at (" + command.x() + ", " + command.y() + ") with size " + command.width() + "x" + command.height());
                }
                return null;
            }
        ));
    }
}