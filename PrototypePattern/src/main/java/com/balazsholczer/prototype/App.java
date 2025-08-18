package com.balazsholczer.prototype;

/**
 * Prototype Pattern: creates objects by cloning existing instances
 * 
 * Key Concepts:
 * - Creates new objects by copying existing ones (prototypes)
 * - Avoids expensive object creation by cloning
 * - Hides object creation complexity from client
 * - Supports both shallow and deep copying
 * 
 * Structure:
 * - Prototype: declares cloning interface
 * - ConcretePrototype: implements cloning operation
 * - Client: creates objects by asking prototype to clone itself
 * - Registry: manages prototype instances (optional)
 * 
 * Benefits:
 * - Reduces object creation cost when creation is expensive
 * - Hides object creation complexity
 * - Allows runtime configuration of object types
 * - Supports adding/removing prototypes dynamically
 * 
 * Use Cases:
 * - Database record templates (avoid repeated queries)
 * - Game object spawning (characters, items, enemies)
 * - Document templates (forms, reports, layouts)
 * - Configuration objects (settings, preferences)
 * - Complex object initialization (avoid repeated setup)
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Prototype Pattern ===");
        
        // Create and register prototypes
        Book programmingBook = new Book("Design Patterns", "Gang of Four", 395, "Programming");
        Book fictionBook = new Book("The Hobbit", "J.R.R. Tolkien", 310, "Fantasy");
        
        PrototypeRegistry registry = new PrototypeRegistry();
        registry.registerPrototype("programming", programmingBook);
        registry.registerPrototype("fiction", fictionBook);
        
        // Clone prototypes to create new instances
        Book newProgrammingBook = (Book) registry.getPrototype("programming");
        Book newFictionBook = (Book) registry.getPrototype("fiction");
        
        // Customize cloned objects
        newProgrammingBook.setTitle("Clean Code");
        newProgrammingBook.setAuthor("Robert Martin");
        
        newFictionBook.setTitle("Lord of the Rings");
        newFictionBook.setPages(1216);
        
        // Show original vs cloned objects
        System.out.println("Original Programming: " + programmingBook);
        System.out.println("Cloned Programming: " + newProgrammingBook);
        System.out.println("Original Fiction: " + fictionBook);
        System.out.println("Cloned Fiction: " + newFictionBook);
        
        // Show registry capabilities
        registry.listPrototypes();
        
        System.out.println("\n=== Run ModernPrototypeDemo for all approaches ===");
    }
}