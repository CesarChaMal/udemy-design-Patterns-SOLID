package com.balazsholczer.flyweight;

import java.util.function.BiConsumer;

public enum EnumFlyweight {
    CIRCLE("Circle", (x, y) -> System.out.println("EnumFlyweight: Circle at (" + x + ", " + y + ")")),
    SQUARE("Square", (x, y) -> System.out.println("EnumFlyweight: Square at (" + x + ", " + y + ")")),
    TRIANGLE("Triangle", (x, y) -> System.out.println("EnumFlyweight: Triangle at (" + x + ", " + y + ")"));
    
    private final String shape;
    private final BiConsumer<Integer, Integer> renderer;
    
    EnumFlyweight(String shape, BiConsumer<Integer, Integer> renderer) {
        this.shape = shape;
        this.renderer = renderer;
    }
    
    public void render(int x, int y) {
        renderer.accept(x, y);
    }
    
    public String getShape() {
        return shape;
    }
    
    public static EnumFlyweight getByShape(String shape) {
        for (EnumFlyweight flyweight : values()) {
            if (flyweight.shape.equalsIgnoreCase(shape)) {
                return flyweight;
            }
        }
        throw new IllegalArgumentException("Unknown shape: " + shape);
    }
    
    public record Context(EnumFlyweight flyweight, int x, int y, String color) {
        public void render() {
            System.out.print("Color: " + color + " - ");
            flyweight.render(x, y);
        }
    }
}