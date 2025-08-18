package com.balazsholczer.command;

import java.util.ArrayList;
import java.util.List;

public class LambdaSwitcher {
    
    private final List<Runnable> commands = new ArrayList<>();
    
    public LambdaSwitcher add(Runnable command) {
        commands.add(command);
        return this;
    }
    
    public void executeAll() {
        commands.forEach(Runnable::run);
    }
}