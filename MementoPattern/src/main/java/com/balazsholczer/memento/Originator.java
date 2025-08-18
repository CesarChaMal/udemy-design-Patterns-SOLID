package com.balazsholczer.memento;

public class Originator {
    
    private String state;
    
    public void setState(String state) {
        this.state = state;
        System.out.println("Originator: State set to " + state);
    }
    
    public String getState() {
        return state;
    }
    
    public Memento saveStateToMemento() {
        System.out.println("Originator: Saving state to memento");
        return new Memento(state);
    }
    
    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
        System.out.println("Originator: State restored from memento to " + state);
    }
}