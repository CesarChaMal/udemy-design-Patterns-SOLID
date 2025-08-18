package com.balazsholczer.bridge;

/**
 * Bridge Pattern: separates abstraction from implementation
 * 
 * Key Concepts:
 * - Decouples abstraction (Shape) from implementation (DrawingAPI)
 * - Both can vary independently without affecting each other
 * - Composition over inheritance
 * - "Prefer composition over inheritance" principle
 * 
 * Structure:
 * - Abstraction: defines high-level operations (Shape)
 * - Refined Abstraction: extends abstraction (Circle, Rectangle)
 * - Implementation: defines low-level operations (DrawingAPI)
 * - Concrete Implementation: implements the interface (OpenGL, DirectX)
 * 
 * Benefits:
 * - Runtime binding of implementation
 * - Sharing implementation among multiple objects
 * - Extensibility: add new abstractions and implementations independently
 * - Hides implementation details from clients
 * 
 * Use Cases:
 * - Cross-platform applications (Windows/Mac/Linux)
 * - Database drivers (MySQL/PostgreSQL with same API)
 * - Graphics rendering (OpenGL/DirectX/Vulkan)
 * - UI frameworks with different themes
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Bridge Pattern ===");
        
        // Same abstraction (Circle) with different implementations
        Shape openglCircle = new Circle(10, 10, 5, new OpenGLDrawingAPI());
        Shape directxCircle = new Circle(10, 10, 5, new DirectXDrawingAPI());
        
        // Same abstraction (Rectangle) with different implementations
        Shape openglRect = new Rectangle(0, 0, 15, 10, new OpenGLDrawingAPI());
        Shape directxRect = new Rectangle(0, 0, 15, 10, new DirectXDrawingAPI());
        
        // Abstraction and implementation can vary independently
        openglCircle.draw();
        directxCircle.draw();
        openglRect.draw();
        directxRect.draw();
        
        System.out.println("\n=== Run ModernBridgeDemo for all approaches ===");
    }
}