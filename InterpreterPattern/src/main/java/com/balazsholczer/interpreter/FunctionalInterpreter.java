package com.balazsholczer.interpreter;

import java.util.function.BinaryOperator;

public class FunctionalInterpreter {
    
    public sealed interface Expr permits NumberExpr, BinaryExpr {}
    
    public record NumberExpr(int value) implements Expr {}
    
    public record BinaryExpr(Expr left, Expr right, BinaryOperator<Integer> operator, String symbol) implements Expr {}
    
    public static int interpret(Expr expr) {
        return switch (expr) {
            case NumberExpr(var value) -> value;
            case BinaryExpr(var left, var right, var operator, var symbol) -> 
                operator.apply(interpret(left), interpret(right));
        };
    }
    
    public static String toString(Expr expr) {
        return switch (expr) {
            case NumberExpr(var value) -> String.valueOf(value);
            case BinaryExpr(var left, var right, var operator, var symbol) -> 
                "(" + toString(left) + " " + symbol + " " + toString(right) + ")";
        };
    }
    
    // Factory methods
    public static Expr number(int value) {
        return new NumberExpr(value);
    }
    
    public static Expr add(Expr left, Expr right) {
        return new BinaryExpr(left, right, Integer::sum, "+");
    }
    
    public static Expr subtract(Expr left, Expr right) {
        return new BinaryExpr(left, right, (a, b) -> a - b, "-");
    }
    
    public static Expr multiply(Expr left, Expr right) {
        return new BinaryExpr(left, right, (a, b) -> a * b, "*");
    }
    
    public static Expr divide(Expr left, Expr right) {
        return new BinaryExpr(left, right, (a, b) -> a / b, "/");
    }
}