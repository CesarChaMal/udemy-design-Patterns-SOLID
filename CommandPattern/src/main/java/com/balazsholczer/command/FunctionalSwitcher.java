package com.balazsholczer.command;

import java.util.ArrayList;
import java.util.List;

public class FunctionalSwitcher {
    
    private final List<FunctionalCommand> commands = new ArrayList<>();
    
    public FunctionalSwitcher add(FunctionalCommand command) {
        commands.add(command);
        return this;
    }
    
    public void executeAll() {
        commands.forEach(FunctionalCommand::execute);
    }
}