package com.balazsholczer.abstractfactory;

/**
 * Abstract Factory Pattern: provides an interface for creating families of related objects
 * 
 * Key Concepts:
 * - Creates families of related products (Button + Checkbox for same OS)
 * - Ensures products from same family are compatible
 * - Client code works with abstract interfaces, not concrete classes
 * 
 * Benefits:
 * - Ensures product compatibility within families
 * - Isolates concrete classes from client code
 * - Easy to add new product families
 * - Follows Open/Closed Principle
 * 
 * Use Cases:
 * - Cross-platform UI components
 * - Database drivers (MySQL, PostgreSQL families)
 * - Different rendering engines
 * - Theme-based component creation
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Abstract Factory Pattern ===");
        
        // Client code doesn't know which concrete factory it's using
        GUIFactory factory = getFactory("Windows");
        
        // Create family of related products
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        
        // All products from same family work together
        button.render();
        button.onClick();
        checkbox.render();
        checkbox.toggle();
        
        System.out.println("\n=== Run ModernAbstractFactoryDemo for all approaches ===");
    }
    
    private static GUIFactory getFactory(String os) {
        return switch (os) {
            case "Windows" -> new WindowsFactory();
            case "Mac" -> new MacFactory();
            default -> throw new IllegalArgumentException("Unsupported OS: " + os);
        };
    }
}