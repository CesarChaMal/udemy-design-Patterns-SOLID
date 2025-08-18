package com.balazsholczer.bridge;

import java.util.function.Consumer;

public class FunctionalShape {
    
    public record ShapeData(double x, double y, double width, double height) {
        public ShapeData resize(double factor) {
            return new ShapeData(x, y, width * factor, height * factor);
        }
    }
    
    public record Renderer(Consumer<ShapeData> circleRenderer, Consumer<ShapeData> rectangleRenderer) {
        
        public void drawCircle(ShapeData data) {
            circleRenderer.accept(data);
        }
        
        public void drawRectangle(ShapeData data) {
            rectangleRenderer.accept(data);
        }
    }
    
    public static final Renderer OPENGL_RENDERER = new Renderer(
        data -> System.out.println("OpenGL: Drawing circle at (" + data.x + ", " + data.y + ") with radius " + data.width),
        data -> System.out.println("OpenGL: Drawing rectangle at (" + data.x + ", " + data.y + ") with size " + data.width + "x" + data.height)
    );
    
    public static final Renderer DIRECTX_RENDERER = new Renderer(
        data -> System.out.println("DirectX: Drawing circle at (" + data.x + ", " + data.y + ") with radius " + data.width),
        data -> System.out.println("DirectX: Drawing rectangle at (" + data.x + ", " + data.y + ") with size " + data.width + "x" + data.height)
    );
}