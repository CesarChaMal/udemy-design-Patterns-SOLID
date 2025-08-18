package com.balazsholczer.mediator;

/**
 * Mediator Pattern: defines how objects interact with each other
 * 
 * Key Concepts:
 * - Encapsulates how multiple objects interact
 * - Promotes loose coupling by preventing direct references
 * - Centralizes complex communications and control logic
 * - Objects communicate through mediator instead of directly
 * 
 * Structure:
 * - Mediator: defines communication interface
 * - ConcreteMediator: implements mediator, coordinates colleagues
 * - Colleague: communicates through mediator
 * - ConcreteColleague: implements colleague behavior
 * 
 * Benefits:
 * - Reduces dependencies between communicating objects
 * - Centralizes complex communications
 * - Promotes reusability of colleague objects
 * - Makes interaction easier to understand and maintain
 * 
 * Use Cases:
 * - GUI dialog boxes (buttons, fields interact through dialog)
 * - Chat rooms (users communicate through chat room)
 * - Air traffic control (planes communicate through tower)
 * - Event systems (components communicate through event bus)
 * - Workflow systems (steps coordinate through workflow engine)
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Mediator Pattern ===");
        
        // Create mediator
        ConcreteMediator mediator = new ConcreteMediator();
        
        // Create colleagues
        ConcreteColleague alice = new ConcreteColleague(mediator, "Alice");
        ConcreteColleague bob = new ConcreteColleague(mediator, "Bob");
        ConcreteColleague charlie = new ConcreteColleague(mediator, "Charlie");
        
        // Register colleagues with mediator
        mediator.addColleague(alice);
        mediator.addColleague(bob);
        mediator.addColleague(charlie);
        
        // Colleagues communicate through mediator
        alice.send("Hello everyone!");
        bob.send("Hi Alice!");
        charlie.send("Good morning!");
        
        System.out.println("\n=== Run ModernMediatorDemo for all approaches ===");
    }
}