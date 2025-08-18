package com.balazsholczer.bridge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Bridge Pattern
 * Tests Traditional, Enum, Functional, and Generic approaches
 */
class BridgeTest {
    
    @Test
    void testTraditionalBridge() {
        DrawingAPI openGL = new OpenGLDrawingAPI();
        DrawingAPI directX = new DirectXDrawingAPI();
        
        Shape circle1 = new Circle(5, 10, 15, openGL);
        Shape circle2 = new Circle(5, 10, 15, directX);
        Shape rectangle1 = new Rectangle(0, 0, 20, 30, openGL);
        Shape rectangle2 = new Rectangle(0, 0, 20, 30, directX);
        
        assertDoesNotThrow(() -> circle1.draw());
        assertDoesNotThrow(() -> circle2.draw());
        assertDoesNotThrow(() -> rectangle1.draw());
        assertDoesNotThrow(() -> rectangle2.draw());
    }
    
    @Test
    void testEnumShapeBridge() {
        var openGL = EnumShapeBridge.Renderer.OPENGL;
        var directX = EnumShapeBridge.Renderer.DIRECTX;
        
        var circleData = new EnumShapeBridge.ShapeData(5, 10, 15, 15, 15);
        var rectangleData = new EnumShapeBridge.ShapeData(0, 0, 20, 30, 0);
        
        assertDoesNotThrow(() -> openGL.drawCircle(circleData));
        assertDoesNotThrow(() -> directX.drawCircle(circleData));
        assertDoesNotThrow(() -> openGL.drawRectangle(rectangleData));
        assertDoesNotThrow(() -> directX.drawRectangle(rectangleData));
    }
    
    @Test
    void testFunctionalShape() {
        var shapeData = new FunctionalShape.ShapeData(5, 10, 15, 20);
        
        // Test with different renderers
        assertDoesNotThrow(() -> FunctionalShape.OPENGL_RENDERER.drawCircle(shapeData));
        assertDoesNotThrow(() -> FunctionalShape.DIRECTX_RENDERER.drawCircle(shapeData));
        assertDoesNotThrow(() -> FunctionalShape.OPENGL_RENDERER.drawRectangle(shapeData));
        assertDoesNotThrow(() -> FunctionalShape.DIRECTX_RENDERER.drawRectangle(shapeData));
    }
    
    @Test
    void testGenericBridge() {
        var bridge = GenericBridge.createDrawingBridge();
        
        var circleCommand = new GenericBridge.DrawCommand("circle", 5, 10, 15, 15);
        var rectangleCommand = new GenericBridge.DrawCommand("rectangle", 0, 0, 20, 30);
        
        assertDoesNotThrow(() -> bridge.execute("opengl", circleCommand));
        assertDoesNotThrow(() -> bridge.execute("directx", circleCommand));
        assertDoesNotThrow(() -> bridge.execute("opengl", rectangleCommand));
        assertDoesNotThrow(() -> bridge.execute("directx", rectangleCommand));
    }
    
    @Test
    void testAbstractionImplementationSeparation() {
        // Test that abstraction and implementation can vary independently
        DrawingAPI[] apis = {new OpenGLDrawingAPI(), new DirectXDrawingAPI()};
        
        for (DrawingAPI api : apis) {
            Shape circle = new Circle(1, 2, 3, api);
            Shape rectangle = new Rectangle(1, 2, 3, 4, api);
            
            assertDoesNotThrow(() -> circle.draw());
            assertDoesNotThrow(() -> rectangle.draw());
        }
    }
    
    @Test
    void testEquivalence() {
        // All bridge implementations should separate abstraction from implementation
        
        // Traditional - Circle with OpenGL
        DrawingAPI openGL = new OpenGLDrawingAPI();
        Shape traditionalCircle = new Circle(5, 10, 15, openGL);
        
        // Enum - Circle with OpenGL
        var enumOpenGL = EnumShapeBridge.Renderer.OPENGL;
        var enumCircleData = new EnumShapeBridge.ShapeData(5, 10, 15, 15, 15);
        
        // Functional - Circle with OpenGL-like function
        var functionalData = new FunctionalShape.ShapeData(5, 10, 15, 15);
        
        // Generic - Circle with OpenGL-like implementation
        var genericBridge = GenericBridge.createDrawingBridge();
        var genericCommand = new GenericBridge.DrawCommand("circle", 5, 10, 15, 15);
        
        // All should handle drawing operations
        assertDoesNotThrow(() -> traditionalCircle.draw());
        assertDoesNotThrow(() -> enumOpenGL.drawCircle(enumCircleData));
        assertDoesNotThrow(() -> FunctionalShape.OPENGL_RENDERER.drawCircle(functionalData));
        assertDoesNotThrow(() -> genericBridge.execute("opengl", genericCommand));
    }
}