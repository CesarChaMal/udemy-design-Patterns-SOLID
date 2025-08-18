package com.balazsholczer.memento;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Memento Pattern
 * Tests Traditional, Functional, Record, and Stream approaches
 */
class MementoTest {
    
    @Test
    void testTraditionalMemento() {
        Originator originator = new Originator();
        Caretaker careTaker = new Caretaker();
        
        originator.setState("State1");
        careTaker.add(originator.saveStateToMemento());
        
        originator.setState("State2");
        careTaker.add(originator.saveStateToMemento());
        
        originator.setState("State3");
        assertEquals("State3", originator.getState());
        
        originator.getStateFromMemento(careTaker.get(1));
        assertEquals("State2", originator.getState());
        
        originator.getStateFromMemento(careTaker.get(0));
        assertEquals("State1", originator.getState());
    }
    
    @Test
    void testFunctionalMemento() {
        FunctionalMemento.CommandManager<String> manager = new FunctionalMemento.CommandManager<>("Hello");
        
        manager.execute(FunctionalMemento.appendText(" World"));
        assertEquals("Hello World", manager.getState());
        
        manager.execute(FunctionalMemento.appendText("!"));
        assertEquals("Hello World!", manager.getState());
        
        assertTrue(manager.undo());
        assertEquals("Hello World", manager.getState());
        
        assertTrue(manager.undo());
        assertEquals("Hello", manager.getState());
        
        assertTrue(manager.redo());
        assertEquals("Hello World", manager.getState());
    }
    
    @Test
    void testFunctionalMementoInteger() {
        FunctionalMemento.CommandManager<Integer> manager = new FunctionalMemento.CommandManager<>(0);
        
        manager.execute(FunctionalMemento.increment(5));
        assertEquals(5, manager.getState());
        
        manager.execute(FunctionalMemento.increment(3));
        assertEquals(8, manager.getState());
        
        assertTrue(manager.undo());
        assertEquals(5, manager.getState());
        
        assertTrue(manager.canRedo());
        assertTrue(manager.redo());
        assertEquals(8, manager.getState());
    }
    
    @Test
    void testRecordMemento() {
        RecordMemento.StateManager<String> manager = new RecordMemento.StateManager<>();
        
        manager.setState("Initial State", "Setup");
        assertEquals("Initial State", manager.getState());
        
        manager.setState("Modified State", "Update");
        assertEquals("Modified State", manager.getState());
        
        assertTrue(manager.undo());
        assertEquals("Initial State", manager.getState());
        
        assertTrue(manager.redo());
        assertEquals("Modified State", manager.getState());
        
        List<RecordMemento.Snapshot<String>> history = manager.getHistory();
        assertEquals(2, history.size());
        assertEquals("Setup", history.get(0).description());
        assertEquals("Update", history.get(1).description());
    }
    
    @Test
    void testStreamMemento() {
        StreamMemento.StreamStateManager<String> manager = new StreamMemento.StreamStateManager<>("Initial");
        
        manager.setState("State1");
        manager.saveSnapshot("checkpoint1", "First checkpoint");
        
        manager.setState("State2");
        manager.saveSnapshot("checkpoint2", "Second checkpoint");
        
        manager.setState("State3");
        
        assertTrue(manager.restoreById("checkpoint1").isPresent());
        assertEquals("State1", manager.getState());
        
        assertTrue(manager.restoreByTag("Second checkpoint").isPresent());
        assertEquals("State2", manager.getState());
        
        List<StreamMemento.StateSnapshot<String>> snapshots = manager.getSnapshotsByTag("First checkpoint");
        assertEquals(1, snapshots.size());
        assertEquals("State1", snapshots.get(0).state());
    }
    
    @Test
    void testMementoPatternBehavior() {
        // Test that all approaches preserve and restore state correctly
        
        // Traditional
        Originator traditional = new Originator();
        traditional.setState("Original");
        Memento memento = traditional.saveStateToMemento();
        traditional.setState("Changed");
        traditional.getStateFromMemento(memento);
        assertEquals("Original", traditional.getState());
        
        // Functional
        FunctionalMemento.CommandManager<String> functional = new FunctionalMemento.CommandManager<>("Start");
        functional.execute(FunctionalMemento.appendText("End"));
        assertEquals("StartEnd", functional.getState());
        functional.undo();
        assertEquals("Start", functional.getState());
        
        // Record
        RecordMemento.StateManager<String> record = new RecordMemento.StateManager<>();
        record.setState("First");
        record.setState("Second");
        record.undo();
        assertEquals("First", record.getState());
        
        // Stream
        StreamMemento.StreamStateManager<String> stream = new StreamMemento.StreamStateManager<>("Begin");
        stream.setState("End");
        stream.restoreByTag("Initial state");
        assertEquals("Begin", stream.getState());
    }
    
    @Test
    void testCareTakerBehavior() {
        Caretaker careTaker = new Caretaker();
        Originator originator = new Originator();
        
        originator.setState("State1");
        careTaker.add(originator.saveStateToMemento());
        
        originator.setState("State2");
        careTaker.add(originator.saveStateToMemento());
        
        assertEquals(2, careTaker.size());
        
        Memento firstMemento = careTaker.get(0);
        Memento secondMemento = careTaker.get(1);
        
        assertNotNull(firstMemento);
        assertNotNull(secondMemento);
        
        originator.getStateFromMemento(firstMemento);
        assertEquals("State1", originator.getState());
    }
    
    @Test
    void testFunctionalMementoUndoRedo() {
        FunctionalMemento.CommandManager<Integer> manager = new FunctionalMemento.CommandManager<>(10);
        
        assertFalse(manager.canUndo());
        assertFalse(manager.canRedo());
        
        manager.execute(FunctionalMemento.increment(5));
        assertTrue(manager.canUndo());
        assertFalse(manager.canRedo());
        
        manager.undo();
        assertFalse(manager.canUndo());
        assertTrue(manager.canRedo());
        
        manager.redo();
        assertTrue(manager.canUndo());
        assertFalse(manager.canRedo());
    }
    
    @Test
    void testRecordMementoHistory() {
        RecordMemento.StateManager<Integer> manager = new RecordMemento.StateManager<>();
        
        manager.setState(1, "First");
        manager.setState(2, "Second");
        manager.setState(3, "Third");
        
        List<RecordMemento.Snapshot<Integer>> history = manager.getHistory();
        assertEquals(3, history.size());
        
        assertEquals(1, history.get(0).state());
        assertEquals(2, history.get(1).state());
        assertEquals(3, history.get(2).state());
        
        assertEquals("First", history.get(0).description());
        assertEquals("Second", history.get(1).description());
        assertEquals("Third", history.get(2).description());
    }
    
    @Test
    void testStreamMementoFiltering() {
        StreamMemento.StreamStateManager<String> manager = new StreamMemento.StreamStateManager<>("Start");
        
        manager.setState("State1");
        manager.saveSnapshot("s1", "tag1");
        
        manager.setState("State2");
        manager.saveSnapshot("s2", "tag2");
        
        manager.setState("State3");
        manager.saveSnapshot("s3", "tag1");
        
        List<StreamMemento.StateSnapshot<String>> tag1Snapshots = manager.getSnapshotsByTag("tag1");
        assertEquals(2, tag1Snapshots.size());
        
        LocalDateTime now = LocalDateTime.now();
        List<StreamMemento.StateSnapshot<String>> recentSnapshots = manager.getSnapshotsBefore(now.plusMinutes(1));
        assertTrue(recentSnapshots.size() >= 4); // Initial + 3 saves
        
        // Test cleanup
        manager.keepOnlyLatest(2);
        assertTrue(manager.getStateHistory().count() <= 2);
    }
    
    @Test
    void testEquivalence() {
        // All approaches should preserve and restore state
        
        // Traditional preserves exact state
        Originator originator = new Originator();
        originator.setState("Test");
        Memento saved = originator.saveStateToMemento();
        originator.setState("Changed");
        originator.getStateFromMemento(saved);
        assertEquals("Test", originator.getState());
        
        // Functional preserves through undo/redo
        FunctionalMemento.CommandManager<String> functional = new FunctionalMemento.CommandManager<>("Test");
        functional.execute(FunctionalMemento.appendText("Changed"));
        functional.undo();
        assertEquals("Test", functional.getState());
        
        // Record preserves with history
        RecordMemento.StateManager<String> record = new RecordMemento.StateManager<>();
        record.setState("Test");
        record.setState("Changed");
        record.undo();
        assertEquals("Test", record.getState());
        
        // Stream preserves with snapshots
        StreamMemento.StreamStateManager<String> stream = new StreamMemento.StreamStateManager<>("Test");
        stream.setState("Changed");
        stream.restoreByTag("Initial state");
        assertEquals("Test", stream.getState());
    }
}