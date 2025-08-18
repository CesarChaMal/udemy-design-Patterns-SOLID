package com.balazsholczer.strategy;

import java.util.function.BinaryOperator;

public class EnumStrategyManager {
    
    public enum Operation implements BinaryOperator<Integer> {
        ADD(Integer::sum),
        MULTIPLY((a, b) -> a * b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> b != 0 ? a / b : 0);
        
        private final BinaryOperator<Integer> operation;
        
        Operation(BinaryOperator<Integer> operation) {
            this.operation = operation;
        }
        
        @Override
        public Integer apply(Integer a, Integer b) {
            return operation.apply(a, b);
        }
    }
    
    private Operation strategy;
    
    public void setStrategy(Operation strategy) {
        this.strategy = strategy;
    }
    
    public int operation(int num1, int num2) {
        return strategy.apply(num1, num2);
    }
}