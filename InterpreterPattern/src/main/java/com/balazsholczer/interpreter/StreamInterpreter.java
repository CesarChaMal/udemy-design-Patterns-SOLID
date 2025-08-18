package com.balazsholczer.interpreter;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class StreamInterpreter {
    
    public record Token(String value, TokenType type) {}
    
    public enum TokenType {
        NUMBER, OPERATOR
    }
    
    public static int evaluate(String expression) {
        return Arrays.stream(expression.split(" "))
                    .map(StreamInterpreter::parseToken)
                    .reduce(new Stack<Integer>(), 
                           StreamInterpreter::processToken, 
                           StreamInterpreter::combineStacks)
                    .pop();
    }
    
    private static Token parseToken(String value) {
        try {
            Integer.parseInt(value);
            return new Token(value, TokenType.NUMBER);
        } catch (NumberFormatException e) {
            return new Token(value, TokenType.OPERATOR);
        }
    }
    
    private static Stack<Integer> processToken(Stack<Integer> stack, Token token) {
        switch (token.type()) {
            case NUMBER -> stack.push(Integer.parseInt(token.value()));
            case OPERATOR -> {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                int right = stack.pop();
                int left = stack.pop();
                int result = applyOperator(token.value(), left, right);
                stack.push(result);
            }
        }
        return stack;
    }
    
    private static Stack<Integer> combineStacks(Stack<Integer> stack1, Stack<Integer> stack2) {
        stack1.addAll(stack2);
        return stack1;
    }
    
    private static int applyOperator(String operator, int left, int right) {
        return switch (operator) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
    
    // Alternative stream-based approach with custom collectors
    public static List<Integer> evaluateMultiple(List<String> expressions) {
        return expressions.stream()
                         .mapToInt(StreamInterpreter::evaluate)
                         .boxed()
                         .toList();
    }
    
    // Functional composition approach
    public static BinaryOperator<Integer> getOperator(String symbol) {
        return switch (symbol) {
            case "+" -> Integer::sum;
            case "-" -> (a, b) -> a - b;
            case "*" -> (a, b) -> a * b;
            case "/" -> (a, b) -> a / b;
            default -> throw new IllegalArgumentException("Unknown operator: " + symbol);
        };
    }
}