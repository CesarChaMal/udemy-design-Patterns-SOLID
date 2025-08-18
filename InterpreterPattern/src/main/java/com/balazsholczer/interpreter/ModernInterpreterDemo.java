package com.balazsholczer.interpreter;

import java.util.List;
import java.util.Map;

public class ModernInterpreterDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Interpreter Pattern ===");
        
        // Build expression tree: (5 + 3) - (2 + 1)
        Expression expr = new SubtractExpression(
            new AddExpression(new NumberExpression(5), new NumberExpression(3)),
            new AddExpression(new NumberExpression(2), new NumberExpression(1))
        );
        
        System.out.println("Expression: " + expr);
        System.out.println("Result: " + expr.interpret());
        
        // Parse postfix expression
        Expression parsed = ExpressionParser.parse("5 3 + 2 1 + -");
        System.out.println("Parsed: " + parsed);
        System.out.println("Result: " + parsed.interpret());
        
        System.out.println("\n=== Functional Interpreter Pattern ===");
        
        // Build expression using functional approach
        var funcExpr = FunctionalInterpreter.subtract(
            FunctionalInterpreter.add(
                FunctionalInterpreter.number(5), 
                FunctionalInterpreter.number(3)
            ),
            FunctionalInterpreter.add(
                FunctionalInterpreter.number(2), 
                FunctionalInterpreter.number(1)
            )
        );
        
        System.out.println("Functional Expression: " + FunctionalInterpreter.toString(funcExpr));
        System.out.println("Result: " + FunctionalInterpreter.interpret(funcExpr));
        
        // More complex expression with multiplication
        var complexExpr = FunctionalInterpreter.multiply(
            FunctionalInterpreter.add(FunctionalInterpreter.number(2), FunctionalInterpreter.number(3)),
            FunctionalInterpreter.subtract(FunctionalInterpreter.number(10), FunctionalInterpreter.number(4))
        );
        
        System.out.println("Complex: " + FunctionalInterpreter.toString(complexExpr));
        System.out.println("Result: " + FunctionalInterpreter.interpret(complexExpr));
        
        System.out.println("\n=== Stream Interpreter Pattern ===");
        
        // Evaluate postfix expressions using streams
        String[] expressions = {"5 3 +", "10 2 -", "4 3 *", "15 3 /"};
        
        for (String expr2 : expressions) {
            int result = StreamInterpreter.evaluate(expr2);
            System.out.println("Stream: " + expr2 + " = " + result);
        }
        
        // Batch evaluation
        List<String> batchExpressions = List.of("5 3 +", "10 2 -", "4 3 *");
        List<Integer> results = StreamInterpreter.evaluateMultiple(batchExpressions);
        System.out.println("Batch results: " + results);
        
        System.out.println("\n=== Lambda Interpreter Pattern ===");
        
        // Create context with variables
        var context = new LambdaInterpreter.Context(Map.of("x", 10, "y", 5, "z", 2));
        
        // Build expressions with variables
        var varExpr = LambdaInterpreter.add(
            LambdaInterpreter.variable("x"),
            LambdaInterpreter.multiply(
                LambdaInterpreter.variable("y"),
                LambdaInterpreter.variable("z")
            )
        );
        
        System.out.println("Variable expression: x + (y * z)");
        System.out.println("With x=10, y=5, z=2: " + varExpr.apply(context));
        
        // Conditional expression
        var conditionalExpr = LambdaInterpreter.ifThenElse(
            LambdaInterpreter.greaterThan(LambdaInterpreter.variable("x"), LambdaInterpreter.constant(5)),
            LambdaInterpreter.constant(100),
            LambdaInterpreter.constant(0)
        );
        
        System.out.println("Conditional: if x > 5 then 100 else 0");
        System.out.println("Result: " + conditionalExpr.apply(context));
        
        System.out.println("\n=== Advanced Features ===");
        
        System.out.println("Traditional - Complex nested expression:");
        Expression nested = new AddExpression(
            new SubtractExpression(new NumberExpression(10), new NumberExpression(3)),
            new AddExpression(new NumberExpression(2), new NumberExpression(5))
        );
        System.out.println(nested + " = " + nested.interpret());
        
        System.out.println("Lambda - Builder pattern:");
        var builderExpr = LambdaInterpreter.ExpressionBuilder
            .start(LambdaInterpreter.constant(10))
            .add(LambdaInterpreter.constant(5))
            .subtract(LambdaInterpreter.constant(3))
            .multiply(LambdaInterpreter.constant(2))
            .build();
        
        System.out.println("Builder: ((10 + 5) - 3) * 2 = " + builderExpr.apply(context));
        
        System.out.println("Stream - Functional operators:");
        var addOp = StreamInterpreter.getOperator("+");
        var mulOp = StreamInterpreter.getOperator("*");
        System.out.println("5 + 3 = " + addOp.apply(5, 3));
        System.out.println("4 * 6 = " + mulOp.apply(4, 6));
        
        System.out.println("Lambda - Dynamic context:");
        var newContext = context.with("a", 20).with("b", 3);
        var dynamicExpr = LambdaInterpreter.subtract(LambdaInterpreter.variable("a"), LambdaInterpreter.variable("b"));
        System.out.println("Dynamic: a - b = " + dynamicExpr.apply(newContext));
    }
}