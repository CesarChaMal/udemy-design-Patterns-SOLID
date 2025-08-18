package com.balazsholczer.interpreter;

import java.util.Stack;

public class ExpressionParser {
    
    public static Expression parse(String expression) {
        String[] tokens = expression.split(" ");
        Stack<Expression> stack = new Stack<>();
        
        for (String token : tokens) {
            switch (token) {
                case "+" -> {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    stack.push(new AddExpression(left, right));
                }
                case "-" -> {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    stack.push(new SubtractExpression(left, right));
                }
                default -> {
                    try {
                        int number = Integer.parseInt(token);
                        stack.push(new NumberExpression(number));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid token: " + token);
                    }
                }
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }
        
        return stack.pop();
    }
}