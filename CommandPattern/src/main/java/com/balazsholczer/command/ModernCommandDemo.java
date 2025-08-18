package com.balazsholczer.command;

import java.util.List;

public class ModernCommandDemo {
    
    public static void main(String[] args) {
        
        Light light = new Light();
        
        System.out.println("=== Traditional Command Pattern ===");
        Switcher traditional = new Switcher();
        traditional.addCommand(new TurnOffCommand(light));
        traditional.addCommand(new TurnOnCommand(light));
        traditional.executeCommands();
        
        System.out.println("\n=== Functional Command Pattern ===");
        new FunctionalSwitcher()
            .add(FunctionalCommand.turnOff(light))
            .add(FunctionalCommand.turnOn(light))
            .executeAll();
        
        System.out.println("\n=== Lambda Command Pattern ===");
        new LambdaSwitcher()
            .add(light::turnOff)
            .add(light::turnOn)
            .executeAll();
        
        System.out.println("\n=== Record Command Pattern ===");
        List.of(
            CommandRecord.turnOff(light),
            CommandRecord.turnOn(light)
        ).forEach(CommandRecord::execute);
    }
}