package com.balazsholczer.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Command Pattern
 * Tests Traditional, Functional, and Lambda approaches
 */
class CommandTest {
    
    @Test
    void testTraditionalCommand() {
        Switcher switcher = new Switcher();
        Light light = new Light();
        
        TurnOnCommand onCommand = new TurnOnCommand(light);
        TurnOffCommand offCommand = new TurnOffCommand(light);
        
        switcher.addCommand(onCommand);
        switcher.addCommand(offCommand);
        
        assertDoesNotThrow(() -> switcher.executeCommands());
    }
    
    @Test
    void testFunctionalCommand() {
        FunctionalSwitcher switcher = new FunctionalSwitcher();
        Light light = new Light();
        
        switcher.add(FunctionalCommand.turnOn(light))
                .add(FunctionalCommand.turnOff(light));
        
        assertDoesNotThrow(() -> switcher.executeAll());
    }
    
    @Test
    void testLambdaSwitcher() {
        LambdaSwitcher switcher = new LambdaSwitcher();
        Light light = new Light();
        
        switcher.add(light::turnOn)
                .add(light::turnOff)
                .add(() -> System.out.println("Custom command"));
        
        assertDoesNotThrow(() -> switcher.executeAll());
    }
    
    @Test
    void testCommandExecution() {
        Light light = new Light();
        
        // Test individual commands
        TurnOnCommand onCommand = new TurnOnCommand(light);
        TurnOffCommand offCommand = new TurnOffCommand(light);
        
        assertDoesNotThrow(() -> onCommand.execute());
        assertDoesNotThrow(() -> offCommand.execute());
    }
    
    @Test
    void testFunctionalCommandCreation() {
        Light light = new Light();
        
        FunctionalCommand turnOn = FunctionalCommand.turnOn(light);
        FunctionalCommand turnOff = FunctionalCommand.turnOff(light);
        
        assertDoesNotThrow(() -> turnOn.execute());
        assertDoesNotThrow(() -> turnOff.execute());
    }
    
    @Test
    void testMultipleCommands() {
        Switcher traditionalSwitcher = new Switcher();
        FunctionalSwitcher functionalSwitcher = new FunctionalSwitcher();
        LambdaSwitcher lambdaSwitcher = new LambdaSwitcher();
        
        Light light1 = new Light();
        Light light2 = new Light();
        
        // Traditional approach
        traditionalSwitcher.addCommand(new TurnOnCommand(light1));
        traditionalSwitcher.addCommand(new TurnOffCommand(light1));
        traditionalSwitcher.addCommand(new TurnOnCommand(light2));
        
        // Functional approach
        functionalSwitcher.add(FunctionalCommand.turnOn(light1))
                         .add(FunctionalCommand.turnOff(light1))
                         .add(FunctionalCommand.turnOn(light2));
        
        // Lambda approach
        lambdaSwitcher.add(light1::turnOn)
                     .add(light1::turnOff)
                     .add(light2::turnOn);
        
        assertDoesNotThrow(() -> traditionalSwitcher.executeCommands());
        assertDoesNotThrow(() -> functionalSwitcher.executeAll());
        assertDoesNotThrow(() -> lambdaSwitcher.executeAll());
    }
    
    @Test
    void testEquivalence() {
        // All approaches should execute the same commands
        Light light = new Light();
        
        // Traditional
        Switcher traditional = new Switcher();
        traditional.addCommand(new TurnOnCommand(light));
        traditional.addCommand(new TurnOffCommand(light));
        
        // Functional
        FunctionalSwitcher functional = new FunctionalSwitcher();
        functional.add(FunctionalCommand.turnOn(light))
                 .add(FunctionalCommand.turnOff(light));
        
        // Lambda
        LambdaSwitcher lambda = new LambdaSwitcher();
        lambda.add(light::turnOn)
              .add(light::turnOff);
        
        // All should execute without throwing
        assertDoesNotThrow(() -> traditional.executeCommands());
        assertDoesNotThrow(() -> functional.executeAll());
        assertDoesNotThrow(() -> lambda.executeAll());
    }
    
    @Test
    void testFluentInterface() {
        Light light = new Light();
        
        // Test fluent interface of modern approaches
        FunctionalSwitcher functionalSwitcher = new FunctionalSwitcher()
            .add(FunctionalCommand.turnOn(light))
            .add(FunctionalCommand.turnOff(light));
        
        LambdaSwitcher lambdaSwitcher = new LambdaSwitcher()
            .add(light::turnOn)
            .add(light::turnOff);
        
        assertDoesNotThrow(() -> functionalSwitcher.executeAll());
        assertDoesNotThrow(() -> lambdaSwitcher.executeAll());
    }
}