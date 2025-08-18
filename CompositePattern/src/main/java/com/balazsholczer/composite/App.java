package com.balazsholczer.composite;

/**
 * Composite Pattern: treats individual objects and compositions uniformly
 * 
 * Key Concepts:
 * - Composes objects into tree structures
 * - Represents part-whole hierarchies
 * - Clients treat individual objects and compositions uniformly
 * - Recursive composition of objects
 * 
 * Structure:
 * - Component: defines interface for all objects in composition
 * - Leaf: represents individual objects (no children)
 * - Composite: represents compositions (has children)
 * - Client: manipulates objects through Component interface
 * 
 * Benefits:
 * - Simplifies client code (uniform treatment)
 * - Easy to add new component types
 * - Flexible tree structures
 * - Recursive operations on hierarchies
 * 
 * Use Cases:
 * - File system hierarchies (files and directories)
 * - GUI component trees (panels, buttons, containers)
 * - Organization structures (employees, departments)
 * - Mathematical expressions (numbers, operators)
 * - Menu systems (items, submenus)
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Composite Pattern ===");
        
        // Create tree structure: Root -> Branch1 -> Leaf1, Leaf2
        //                              -> Branch2 -> Leaf3
        Component root = new Composite("Root");
        Component branch1 = new Composite("Branch1");
        Component branch2 = new Composite("Branch2");
        
        Component leaf1 = new Leaf("Leaf1");
        Component leaf2 = new Leaf("Leaf2");
        Component leaf3 = new Leaf("Leaf3");
        
        // Build the tree
        root.add(branch1);
        root.add(branch2);
        branch1.add(leaf1);
        branch1.add(leaf2);
        branch2.add(leaf3);
        
        // Uniform treatment: same operation on entire tree
        root.operation();
        
        System.out.println("\n=== Run ModernCompositeDemo for all approaches ===");
    }
}