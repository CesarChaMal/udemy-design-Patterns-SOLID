package com.balazsholczer.decorator;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class StreamBeverage {
    
    public record BeverageData(int cost, String description) {}
    
    public static final UnaryOperator<BeverageData> MILK = 
        b -> new BeverageData(b.cost + 3, b.description + "milk ");
    
    public static final UnaryOperator<BeverageData> SUGAR = 
        b -> new BeverageData(b.cost + 1, b.description + "sugar ");
    
    @SafeVarargs
    public static BeverageData decorate(UnaryOperator<BeverageData>... decorators) {
        BeverageData result = new BeverageData(5, "");
        for (UnaryOperator<BeverageData> decorator : decorators) {
            result = decorator.apply(result);
        }
        return result;
    }
}