package com.balazsholczer.interpreter;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaInterpreter {
    
    public record Context(Map<String, Integer> variables) {
        public Context with(String name, int value) {
            var newVars = new java.util.HashMap<>(variables);
            newVars.put(name, value);
            return new Context(newVars);
        }
    }
    
    @FunctionalInterface
    public interface Evaluator extends Function<Context, Integer> {}
    
    // Terminal expressions
    public static Evaluator constant(int value) {
        return context -> value;
    }
    
    public static Evaluator variable(String name) {
        return context -> context.variables().getOrDefault(name, 0);
    }
    
    // Non-terminal expressions
    public static Evaluator add(Evaluator left, Evaluator right) {
        return context -> left.apply(context) + right.apply(context);
    }
    
    public static Evaluator subtract(Evaluator left, Evaluator right) {
        return context -> left.apply(context) - right.apply(context);
    }
    
    public static Evaluator multiply(Evaluator left, Evaluator right) {
        return context -> left.apply(context) * right.apply(context);
    }
    
    public static Evaluator divide(Evaluator left, Evaluator right) {
        return context -> left.apply(context) / right.apply(context);
    }
    
    // Conditional expressions
    public static Evaluator ifThenElse(Evaluator condition, Evaluator thenExpr, Evaluator elseExpr) {
        return context -> condition.apply(context) != 0 ? thenExpr.apply(context) : elseExpr.apply(context);
    }
    
    // Comparison expressions
    public static Evaluator greaterThan(Evaluator left, Evaluator right) {
        return context -> left.apply(context) > right.apply(context) ? 1 : 0;
    }
    
    public static Evaluator equals(Evaluator left, Evaluator right) {
        return context -> left.apply(context).equals(right.apply(context)) ? 1 : 0;
    }
    
    // Builder pattern for complex expressions
    public static class ExpressionBuilder {
        private Evaluator expression;
        
        public ExpressionBuilder(Evaluator initial) {
            this.expression = initial;
        }
        
        public ExpressionBuilder add(Evaluator other) {
            this.expression = LambdaInterpreter.add(this.expression, other);
            return this;
        }
        
        public ExpressionBuilder subtract(Evaluator other) {
            this.expression = LambdaInterpreter.subtract(this.expression, other);
            return this;
        }
        
        public ExpressionBuilder multiply(Evaluator other) {
            this.expression = LambdaInterpreter.multiply(this.expression, other);
            return this;
        }
        
        public Evaluator build() {
            return expression;
        }
        
        public static ExpressionBuilder start(Evaluator initial) {
            return new ExpressionBuilder(initial);
        }
    }
}