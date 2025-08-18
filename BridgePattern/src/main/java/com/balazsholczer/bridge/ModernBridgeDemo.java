package com.balazsholczer.bridge;

import com.balazsholczer.bridge.EnumShapeBridge.Renderer;
import com.balazsholczer.bridge.EnumShapeBridge.ShapeData;

public class ModernBridgeDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Bridge Pattern ===");
        Shape openglCircle = new Circle(10, 10, 5, new OpenGLDrawingAPI());
        Shape directxCircle = new Circle(20, 20, 8, new DirectXDrawingAPI());
        Shape openglRect = new Rectangle(0, 0, 15, 10, new OpenGLDrawingAPI());
        Shape directxRect = new Rectangle(5, 5, 20, 12, new DirectXDrawingAPI());
        
        openglCircle.draw();
        directxCircle.draw();
        openglRect.draw();
        directxRect.draw();
        
        System.out.println("\n=== Functional Bridge Pattern ===");
        var circleData = new FunctionalShape.ShapeData(10, 10, 5, 5);
        var rectData = new FunctionalShape.ShapeData(0, 0, 15, 10);
        
        FunctionalShape.OPENGL_RENDERER.drawCircle(circleData);
        FunctionalShape.DIRECTX_RENDERER.drawCircle(circleData);
        FunctionalShape.OPENGL_RENDERER.drawRectangle(rectData);
        FunctionalShape.DIRECTX_RENDERER.drawRectangle(rectData);
        
        System.out.println("\n=== Enum Bridge Pattern ===");
        ShapeData enumCircle = new ShapeData(10, 10, 15, 10, 5);
        ShapeData enumRect = new ShapeData(0, 0, 15, 10, 0);
        
        Renderer.OPENGL.drawCircle(enumCircle);
        Renderer.DIRECTX.drawCircle(enumCircle);
        Renderer.OPENGL.drawRectangle(enumRect);
        Renderer.DIRECTX.drawRectangle(enumRect);
        
        System.out.println("\n=== Generic Bridge Pattern ===");
        GenericBridge<GenericBridge.DrawCommand, Void> bridge = GenericBridge.createDrawingBridge();
        
        var circleCommand = new GenericBridge.DrawCommand("circle", 10, 10, 5, 5);
        var rectCommand = new GenericBridge.DrawCommand("rectangle", 0, 0, 15, 10);
        
        bridge.execute("opengl", circleCommand);
        bridge.execute("directx", circleCommand);
        bridge.execute("opengl", rectCommand);
        bridge.execute("directx", rectCommand);
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Traditional - Resize and redraw:");
        openglCircle.resize(2.0);
        openglCircle.draw();
        
        System.out.println("Functional - Immutable resize:");
        var resizedData = circleData.resize(2.0);
        FunctionalShape.OPENGL_RENDERER.drawCircle(resizedData);
        
        System.out.println("Enum - All Renderers:");
        for (Renderer renderer : Renderer.values()) {
            System.out.println("Using " + renderer.name() + " renderer:");
            renderer.drawCircle(enumCircle);
        }
        
        System.out.println("Generic - Error handling:");
        try {
            bridge.execute("vulkan", circleCommand);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}