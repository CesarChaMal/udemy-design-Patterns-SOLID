package com.balazsholczer.memento;

/**
 * Memento Pattern: captures and restores object state without violating encapsulation
 * 
 * Key Concepts:
 * - Captures internal state of an object without violating encapsulation
 * - Allows object to be restored to previous state (undo mechanism)
 * - Three roles: Originator, Memento, Caretaker
 * - State is stored externally but remains opaque to caretaker
 * 
 * Structure:
 * - Originator: creates memento and uses it to restore state
 * - Memento: stores internal state of originator
 * - Caretaker: manages mementos but never operates on their content
 * 
 * Benefits:
 * - Preserves encapsulation boundaries
 * - Simplifies originator by delegating state management
 * - Provides undo/redo functionality
 * - Isolates state storage from business logic
 * 
 * Use Cases:
 * - Text editors (undo/redo operations)
 * - Game development (save/load game states)
 * - Database transactions (rollback mechanisms)
 * - Configuration management (backup/restore settings)
 * - Version control systems (commit snapshots)
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Memento Pattern ===");
        
        // Create originator and caretaker
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        
        // Change state and save snapshots
        originator.setState("State #1");
        caretaker.add(originator.saveStateToMemento());
        
        originator.setState("State #2");
        caretaker.add(originator.saveStateToMemento());
        
        originator.setState("State #3");
        System.out.println("Current state: " + originator.getState());
        
        // Restore to previous states
        System.out.println("\nRestoring previous states:");
        originator.getStateFromMemento(caretaker.get(1)); // State #2
        originator.getStateFromMemento(caretaker.get(0)); // State #1
        
        // Show memento history
        caretaker.showHistory();
        
        System.out.println("\n=== Run ModernMementoDemo for all approaches ===");
    }
}