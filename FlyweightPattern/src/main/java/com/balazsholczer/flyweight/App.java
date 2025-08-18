package com.balazsholczer.flyweight;

/**
 * Flyweight Pattern: minimizes memory usage by sharing common data
 * 
 * Key Concepts:
 * - Intrinsic State: shared data stored in flyweight (immutable)
 * - Extrinsic State: context-specific data passed to operations
 * - Factory: manages and reuses flyweight instances
 * - Reduces memory footprint when dealing with large numbers of similar objects
 * 
 * Structure:
 * - Flyweight: defines interface for flyweights
 * - ConcreteFlyweight: implements flyweight, stores intrinsic state
 * - FlyweightFactory: creates and manages flyweight instances
 * - Context: maintains extrinsic state and references to flyweight
 * 
 * Benefits:
 * - Reduces memory usage significantly
 * - Improves performance by object reuse
 * - Centralizes object creation and management
 * - Separates intrinsic from extrinsic state
 * 
 * Use Cases:
 * - Text editors (character formatting)
 * - Game development (sprites, particles)
 * - GUI systems (icons, buttons)
 * - Graphics rendering (shapes, textures)
 * - Large datasets with repeated elements
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Flyweight Pattern ===");
        
        // Create multiple contexts - flyweights are shared
        Context circle1 = new Context("Circle", "Red at (10, 20)");
        Context circle2 = new Context("Circle", "Blue at (30, 40)");  // Reuses Circle flyweight
        Context square1 = new Context("Square", "Green at (50, 60)");
        Context circle3 = new Context("Circle", "Yellow at (70, 80)"); // Reuses Circle flyweight
        
        // Execute operations
        circle1.operation();
        circle2.operation();
        square1.operation();
        circle3.operation();
        
        // Show memory efficiency
        System.out.println("\nMemory Efficiency:");
        System.out.println("Created 4 contexts but only " + FlyweightFactory.getFlyweightCount() + " flyweight objects");
        FlyweightFactory.printFlyweights();
        
        System.out.println("\n=== Run ModernFlyweightDemo for all approaches ===");
    }
}