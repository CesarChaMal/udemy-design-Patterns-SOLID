package com.balazsholczer.flyweight;

import java.util.List;

public class ModernFlyweightDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Flyweight Pattern ===");
        
        // Create multiple contexts that share flyweights
        Context[] contexts = {
            new Context("Circle", "Red at (10, 20)"),
            new Context("Circle", "Blue at (30, 40)"),
            new Context("Square", "Green at (50, 60)"),
            new Context("Circle", "Yellow at (70, 80)"),
            new Context("Square", "Purple at (90, 100)")
        };
        
        for (Context context : contexts) {
            context.operation();
        }
        
        FlyweightFactory.printFlyweights();
        
        System.out.println("\n=== Record Flyweight Pattern ===");
        
        // Create contexts using record flyweights
        List<RecordFlyweight.Context> recordContexts = List.of(
            RecordFlyweight.createContext("Circle", "Red", 10, 20),
            RecordFlyweight.createContext("Circle", "Blue", 30, 40),
            RecordFlyweight.createContext("Square", "Green", 50, 60),
            RecordFlyweight.createContext("Circle", "Red", 70, 80),
            RecordFlyweight.createContext("Triangle", "Yellow", 90, 100)
        );
        
        recordContexts.forEach(RecordFlyweight.Context::render);
        RecordFlyweight.printStats();
        
        System.out.println("\n=== Enum Flyweight Pattern ===");
        
        // Create contexts using enum flyweights
        List<EnumFlyweight.Context> enumContexts = List.of(
            new EnumFlyweight.Context(EnumFlyweight.CIRCLE, 10, 20, "Red"),
            new EnumFlyweight.Context(EnumFlyweight.CIRCLE, 30, 40, "Blue"),
            new EnumFlyweight.Context(EnumFlyweight.SQUARE, 50, 60, "Green"),
            new EnumFlyweight.Context(EnumFlyweight.TRIANGLE, 70, 80, "Yellow"),
            new EnumFlyweight.Context(EnumFlyweight.CIRCLE, 90, 100, "Purple")
        );
        
        enumContexts.forEach(EnumFlyweight.Context::render);
        
        System.out.println("\n=== Functional Flyweight Pattern ===");
        
        // Create flyweights using functional approach
        var circleIntrinsic = FunctionalFlyweight.CIRCLE_CREATOR.apply("Solid");
        var squareIntrinsic = FunctionalFlyweight.SQUARE_CREATOR.apply("Dashed");
        
        List<FunctionalFlyweight.RenderCommand> commands = List.of(
            new FunctionalFlyweight.RenderCommand(circleIntrinsic, new FunctionalFlyweight.ExtrinsicState(10, 20, "Red")),
            new FunctionalFlyweight.RenderCommand(circleIntrinsic, new FunctionalFlyweight.ExtrinsicState(30, 40, "Blue")),
            new FunctionalFlyweight.RenderCommand(squareIntrinsic, new FunctionalFlyweight.ExtrinsicState(50, 60, "Green")),
            new FunctionalFlyweight.RenderCommand(circleIntrinsic, new FunctionalFlyweight.ExtrinsicState(70, 80, "Yellow"))
        );
        
        commands.forEach(cmd -> System.out.println(FunctionalFlyweight.render(cmd)));
        FunctionalFlyweight.printStats();
        
        System.out.println("\n=== Advanced Features ===");
        
        System.out.println("Traditional - Memory efficiency:");
        System.out.println("Created " + contexts.length + " contexts but only " + FlyweightFactory.getFlyweightCount() + " flyweights");
        
        System.out.println("Enum - Built-in flyweights:");
        for (EnumFlyweight flyweight : EnumFlyweight.values()) {
            System.out.println("Available: " + flyweight.getShape());
        }
        
        System.out.println("Record - Immutable sharing:");
        var sharedFlyweight = RecordFlyweight.getFlyweight("Circle", "Red");
        var context1 = new RecordFlyweight.Context(sharedFlyweight, 100, 200);
        var context2 = new RecordFlyweight.Context(sharedFlyweight, 300, 400);
        System.out.println("Same flyweight instance: " + (context1.flyweight() == context2.flyweight()));
        
        System.out.println("Functional - Composable operations:");
        var customIntrinsic = new FunctionalFlyweight.IntrinsicState("Diamond", "Gradient");
        var result = FunctionalFlyweight.render(customIntrinsic, new FunctionalFlyweight.ExtrinsicState(500, 600, "Gold"));
        System.out.println(result);
    }
}