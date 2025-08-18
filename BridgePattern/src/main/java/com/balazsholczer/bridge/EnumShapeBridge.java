package com.balazsholczer.bridge;

import java.util.function.Consumer;

public class EnumShapeBridge {
    
    public enum Renderer {
        OPENGL(
            data -> System.out.println("OpenGL: Drawing circle at (" + data.x() + ", " + data.y() + ") with radius " + data.radius()),
            data -> System.out.println("OpenGL: Drawing rectangle at (" + data.x() + ", " + data.y() + ") with size " + data.width() + "x" + data.height())
        ),
        DIRECTX(
            data -> System.out.println("DirectX: Drawing circle at (" + data.x() + ", " + data.y() + ") with radius " + data.radius()),
            data -> System.out.println("DirectX: Drawing rectangle at (" + data.x() + ", " + data.y() + ") with size " + data.width() + "x" + data.height())
        );
        
        private final Consumer<ShapeData> circleRenderer;
        private final Consumer<ShapeData> rectangleRenderer;
        
        Renderer(Consumer<ShapeData> circleRenderer, Consumer<ShapeData> rectangleRenderer) {
            this.circleRenderer = circleRenderer;
            this.rectangleRenderer = rectangleRenderer;
        }
        
        public void drawCircle(ShapeData data) {
            circleRenderer.accept(data);
        }
        
        public void drawRectangle(ShapeData data) {
            rectangleRenderer.accept(data);
        }
    }
    
    public record ShapeData(double x, double y, double width, double height, double radius) {
        public ShapeData resize(double factor) {
            return new ShapeData(x, y, width * factor, height * factor, radius * factor);
        }
    }
}