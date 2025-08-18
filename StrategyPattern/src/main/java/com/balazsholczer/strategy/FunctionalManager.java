package com.balazsholczer.strategy;

import java.util.function.BinaryOperator;

public class FunctionalManager {
    
    private BinaryOperator<Integer> strategy;
    
    public void setStrategy(BinaryOperator<Integer> strategy) {
        this.strategy = strategy;
    }
    
    public int operation(int num1, int num2) {
        return strategy.apply(num1, num2);
    }
    
    public static final BinaryOperator<Integer> ADD = Integer::sum;
    public static final BinaryOperator<Integer> MULTIPLY = (a, b) -> a * b;
    public static final BinaryOperator<Integer> SUBTRACT = (a, b) -> a - b;
}