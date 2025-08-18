package com.balazsholczer.memento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RecordMemento {
    
    public record Snapshot<T>(T state, LocalDateTime timestamp, String description) {
        public Snapshot(T state, String description) {
            this(state, LocalDateTime.now(), description);
        }
        
        public Snapshot(T state) {
            this(state, LocalDateTime.now(), "Auto-saved");
        }
    }
    
    public static class StateManager<T> {
        private T currentState;
        private final List<Snapshot<T>> history = new CopyOnWriteArrayList<>();
        private int currentIndex = -1;
        
        public void setState(T state, String description) {
            this.currentState = state;
            
            // Remove any future history if we're not at the end
            if (currentIndex < history.size() - 1) {
                history.subList(currentIndex + 1, history.size()).clear();
            }
            
            history.add(new Snapshot<>(state, description));
            currentIndex = history.size() - 1;
            System.out.println("RecordMemento: State set - " + description);
        }
        
        public void setState(T state) {
            setState(state, "State updated");
        }
        
        public T getState() {
            return currentState;
        }
        
        public boolean undo() {
            if (currentIndex > 0) {
                currentIndex--;
                currentState = history.get(currentIndex).state();
                System.out.println("RecordMemento: Undo to - " + history.get(currentIndex).description());
                return true;
            }
            return false;
        }
        
        public boolean redo() {
            if (currentIndex < history.size() - 1) {
                currentIndex++;
                currentState = history.get(currentIndex).state();
                System.out.println("RecordMemento: Redo to - " + history.get(currentIndex).description());
                return true;
            }
            return false;
        }
        
        public List<Snapshot<T>> getHistory() {
            return List.copyOf(history);
        }
        
        public boolean canUndo() {
            return currentIndex > 0;
        }
        
        public boolean canRedo() {
            return currentIndex < history.size() - 1;
        }
    }
}