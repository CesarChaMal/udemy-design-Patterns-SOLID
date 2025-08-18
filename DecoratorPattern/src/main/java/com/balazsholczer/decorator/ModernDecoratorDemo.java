package com.balazsholczer.decorator;

import static com.balazsholczer.decorator.StreamBeverage.*;

public class ModernDecoratorDemo {
    
    public static void main(String[] args) {
        
        System.out.println("=== Traditional Decorator Pattern ===");
        Beverage traditional = new Sugar(new Sugar(new Milk(new PlainBeverage())));
        System.out.println("Description: " + traditional.getDescription());
        System.out.println("Cost: " + traditional.getCost());
        
        System.out.println("\n=== Functional Decorator Pattern ===");
        FunctionalBeverage functional = FunctionalBeverage.plain()
            .andThen(FunctionalBeverage.milk())
            .andThen(FunctionalBeverage.sugar())
            .andThen(FunctionalBeverage.sugar());
        System.out.println("Description: " + functional.apply(""));
        
        System.out.println("\n=== Record Decorator Pattern ===");
        BeverageRecord record = BeverageRecord.plain()
            .withMilk()
            .withSugar()
            .withSugar();
        System.out.println("Description: " + record.description());
        System.out.println("Cost: " + record.cost());
        
        System.out.println("\n=== Stream Decorator Pattern ===");
        var stream = StreamBeverage.decorate(MILK, SUGAR, SUGAR);
        System.out.println("Description: " + stream.description());
        System.out.println("Cost: " + stream.cost());
    }
}