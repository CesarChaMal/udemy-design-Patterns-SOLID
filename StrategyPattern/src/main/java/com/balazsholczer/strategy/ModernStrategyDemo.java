package com.balazsholczer.strategy;

import com.balazsholczer.strategy.EnumStrategyManager.Operation;

public class ModernStrategyDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Strategy Pattern ===");
        Manager traditional = new Manager();
        traditional.setStrategy(new Multiply());
        traditional.operation(5, 5);
        traditional.setStrategy(new Add());
        traditional.operation(5, 5);
        
        System.out.println("\n=== Functional Strategy Pattern ===");
        FunctionalManager functional = new FunctionalManager();
        functional.setStrategy(FunctionalManager.MULTIPLY);
        System.out.println("Multiply: " + functional.operation(5, 5));
        functional.setStrategy(FunctionalManager.ADD);
        System.out.println("Add: " + functional.operation(5, 5));
        functional.setStrategy((a, b) -> a - b);
        System.out.println("Subtract: " + functional.operation(5, 5));
        
        System.out.println("\n=== Enum Strategy Pattern ===");
        EnumStrategyManager enumManager = new EnumStrategyManager();
        enumManager.setStrategy(Operation.MULTIPLY);
        System.out.println("Multiply: " + enumManager.operation(5, 5));
        enumManager.setStrategy(Operation.ADD);
        System.out.println("Add: " + enumManager.operation(5, 5));
        enumManager.setStrategy(Operation.DIVIDE);
        System.out.println("Divide: " + enumManager.operation(10, 2));
        
        System.out.println("\n=== Stream Strategy Pattern ===");
        System.out.println("Add: " + StreamStrategyManager.execute("add", 5, 5));
        System.out.println("Multiply: " + StreamStrategyManager.execute("multiply", 5, 5));
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Stream - Execute All:");
        StreamStrategyManager.executeAll(6, 3);
        
        System.out.println("Functional - Method Reference:");
        functional.setStrategy(Integer::max);
        System.out.println("Max: " + functional.operation(5, 8));
        
        System.out.println("Enum - All Operations:");
        for (Operation op : Operation.values()) {
            System.out.println(op.name() + ": " + op.apply(12, 4));
        }
    }
}