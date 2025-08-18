package com.balazsholczer.command;

@FunctionalInterface
public interface FunctionalCommand extends Command {
    
    static FunctionalCommand turnOn(Light light) {
        return light::turnOn;
    }
    
    static FunctionalCommand turnOff(Light light) {
        return light::turnOff;
    }
}