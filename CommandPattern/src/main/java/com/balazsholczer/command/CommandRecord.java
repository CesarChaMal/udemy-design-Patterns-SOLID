package com.balazsholczer.command;

public record CommandRecord(String name, Runnable action) {
    
    public void execute() {
        System.out.println("Executing: " + name);
        action.run();
    }
    
    public static CommandRecord turnOn(Light light) {
        return new CommandRecord("Turn On", light::turnOn);
    }
    
    public static CommandRecord turnOff(Light light) {
        return new CommandRecord("Turn Off", light::turnOff);
    }
}