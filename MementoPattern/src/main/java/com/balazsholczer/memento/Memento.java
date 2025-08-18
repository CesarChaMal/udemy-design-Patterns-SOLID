package com.balazsholczer.memento;

public class Memento {
    
    private final String state;
    private final long timestamp;
    
    public Memento(String state) {
        this.state = state;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getState() {
        return state;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return "Memento{state='" + state + "', timestamp=" + timestamp + "}";
    }
}