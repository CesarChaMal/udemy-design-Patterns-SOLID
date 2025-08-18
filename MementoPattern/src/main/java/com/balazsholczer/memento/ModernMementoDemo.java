package com.balazsholczer.memento;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ModernMementoDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Traditional Memento Pattern ===");
        
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        
        // Set states and save mementos
        originator.setState("State #1");
        caretaker.add(originator.saveStateToMemento());
        
        originator.setState("State #2");
        caretaker.add(originator.saveStateToMemento());
        
        originator.setState("State #3");
        caretaker.add(originator.saveStateToMemento());
        
        // Show history
        caretaker.showHistory();
        
        // Restore previous states
        originator.getStateFromMemento(caretaker.get(1));
        originator.getStateFromMemento(caretaker.get(0));
        
        System.out.println("\n=== Record Memento Pattern ===");
        
        RecordMemento.StateManager<String> textManager = new RecordMemento.StateManager<>();
        
        textManager.setState("Hello", "Initial text");
        textManager.setState("Hello World", "Added 'World'");
        textManager.setState("Hello World!", "Added exclamation");
        textManager.setState("Hello Beautiful World!", "Added 'Beautiful'");
        
        System.out.println("Current: " + textManager.getState());
        
        // Undo operations
        textManager.undo();
        textManager.undo();
        System.out.println("After 2 undos: " + textManager.getState());
        
        // Redo operations
        textManager.redo();
        System.out.println("After 1 redo: " + textManager.getState());
        
        System.out.println("\n=== Functional Memento Pattern ===");
        
        FunctionalMemento.CommandManager<String> cmdManager = 
            new FunctionalMemento.CommandManager<>("Start");
        
        // Execute commands
        cmdManager.execute(FunctionalMemento.appendText(" -> Step1"));
        cmdManager.execute(FunctionalMemento.appendText(" -> Step2"));
        cmdManager.execute(FunctionalMemento.appendText(" -> Step3"));
        
        System.out.println("Current: " + cmdManager.getState());
        
        // Undo and redo
        cmdManager.undo();
        cmdManager.undo();
        System.out.println("After undos: " + cmdManager.getState());
        
        cmdManager.redo();
        System.out.println("After redo: " + cmdManager.getState());
        
        // Integer example
        FunctionalMemento.CommandManager<Integer> numManager = 
            new FunctionalMemento.CommandManager<>(0);
        
        numManager.execute(FunctionalMemento.increment(5));
        numManager.execute(FunctionalMemento.increment(3));
        numManager.execute(FunctionalMemento.increment(-2));
        
        System.out.println("Number: " + numManager.getState());
        numManager.undo();
        System.out.println("After undo: " + numManager.getState());
        
        System.out.println("\n=== Stream Memento Pattern ===");
        
        StreamMemento.StreamStateManager<String> streamManager = 
            new StreamMemento.StreamStateManager<>("Initial");
        
        streamManager.setState("Version 1");
        streamManager.saveSnapshot("v1", "milestone");
        
        streamManager.setState("Version 2");
        streamManager.saveSnapshot("v2", "milestone");
        
        streamManager.setState("Version 2.1");
        streamManager.saveSnapshot("v2.1", "bugfix");
        
        streamManager.setState("Version 3");
        streamManager.saveSnapshot("v3", "milestone");
        
        System.out.println("Current: " + streamManager.getState());
        
        // Restore by tag
        streamManager.restoreByTag("milestone");
        System.out.println("Restored by milestone tag: " + streamManager.getState());
        
        // Restore by id
        streamManager.restoreById("v2.1");
        System.out.println("Restored by id v2.1: " + streamManager.getState());
        
        System.out.println("\n=== Advanced Features ===");
        
        System.out.println("Record - History browsing:");
        var history = textManager.getHistory();
        history.forEach(snapshot -> 
            System.out.println("  " + snapshot.timestamp() + ": " + snapshot.description()));
        
        System.out.println("Functional - Command capabilities:");
        System.out.println("Can undo: " + cmdManager.canUndo());
        System.out.println("Can redo: " + cmdManager.canRedo());
        
        System.out.println("Stream - Query snapshots:");
        var milestones = streamManager.getSnapshotsByTag("milestone");
        milestones.forEach(snapshot -> 
            System.out.println("  Milestone: " + snapshot.id() + " - " + snapshot.state()));
        
        System.out.println("Stream - State history:");
        streamManager.getStateHistory()
                    .forEach(state -> System.out.println("  State: " + state));
        
        System.out.println("Stream - Time-based queries:");
        LocalDateTime fiveSecondsAgo = LocalDateTime.now().minusSeconds(5);
        var recentSnapshots = streamManager.getSnapshotsAfter(fiveSecondsAgo);
        System.out.println("Recent snapshots: " + recentSnapshots.size());
        
        System.out.println("Stream - Cleanup:");
        streamManager.keepOnlyLatest(3);
        
        System.out.println("\n=== Pattern Comparison ===");
        System.out.println("Traditional: Basic state saving and restoration");
        System.out.println("Record: Undo/redo with immutable snapshots");
        System.out.println("Functional: Command-based with functional operations");
        System.out.println("Stream: Advanced querying and filtering capabilities");
    }
}