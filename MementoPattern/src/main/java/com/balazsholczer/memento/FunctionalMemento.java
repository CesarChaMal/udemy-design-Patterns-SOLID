package com.balazsholczer.memento;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Stack;

public class FunctionalMemento {
    
    public record Command<T>(Function<T, T> execute, Function<T, T> undo, String description) {
        
        public static <T> Command<T> of(Function<T, T> execute, Function<T, T> undo, String description) {
            return new Command<>(execute, undo, description);
        }
        
        public static <T> Command<T> simple(Function<T, T> execute, String description) {
            return new Command<>(execute, Function.identity(), description);
        }
    }
    
    public static class CommandManager<T> {
        private T state;
        private final Stack<Command<T>> undoStack = new Stack<>();
        private final Stack<Command<T>> redoStack = new Stack<>();
        
        public CommandManager(T initialState) {
            this.state = initialState;
        }
        
        public void execute(Command<T> command) {
            T previousState = state;
            state = command.execute().apply(state);
            
            // Create undo command with previous state
            Command<T> undoCommand = new Command<>(
                current -> previousState,
                current -> state,
                "Undo: " + command.description()
            );
            
            undoStack.push(undoCommand);
            redoStack.clear(); // Clear redo stack when new command is executed
            
            System.out.println("FunctionalMemento: Executed - " + command.description());
        }
        
        public boolean undo() {
            if (!undoStack.isEmpty()) {
                Command<T> undoCommand = undoStack.pop();
                T previousState = state;
                state = undoCommand.execute().apply(state);
                
                // Create redo command
                Command<T> redoCommand = new Command<>(
                    current -> previousState,
                    current -> state,
                    "Redo: " + undoCommand.description().replace("Undo: ", "")
                );
                
                redoStack.push(redoCommand);
                System.out.println("FunctionalMemento: " + undoCommand.description());
                return true;
            }
            return false;
        }
        
        public boolean redo() {
            if (!redoStack.isEmpty()) {
                Command<T> redoCommand = redoStack.pop();
                state = redoCommand.execute().apply(state);
                
                // Create undo command
                Command<T> undoCommand = new Command<>(
                    current -> redoCommand.undo().apply(current),
                    current -> state,
                    "Undo: " + redoCommand.description().replace("Redo: ", "")
                );
                
                undoStack.push(undoCommand);
                System.out.println("FunctionalMemento: " + redoCommand.description());
                return true;
            }
            return false;
        }
        
        public T getState() {
            return state;
        }
        
        public boolean canUndo() {
            return !undoStack.isEmpty();
        }
        
        public boolean canRedo() {
            return !redoStack.isEmpty();
        }
    }
    
    // Common command factories
    public static Command<String> appendText(String text) {
        return Command.of(
            current -> current + text,
            current -> current.substring(0, Math.max(0, current.length() - text.length())),
            "Append: " + text
        );
    }
    
    public static Command<Integer> increment(int amount) {
        return Command.of(
            current -> current + amount,
            current -> current - amount,
            "Increment by " + amount
        );
    }
}