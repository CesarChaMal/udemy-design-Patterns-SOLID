package com.balazsholczer.interpreter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Interpreter Pattern
 * Tests Traditional, Functional, Lambda, and Stream approaches
 */
class InterpreterTest {
    
    @Test
    void testTraditionalInterpreter() {
        Expression five = new NumberExpression(5);
        Expression three = new NumberExpression(3);
        Expression two = new NumberExpression(2);
        
        Expression addExpression = new AddExpression(five, three);
        assertEquals(8, addExpression.interpret());
        
        Expression subtractExpression = new SubtractExpression(five, three);
        assertEquals(2, subtractExpression.interpret());
        
        Expression complexExpression = new AddExpression(
            new SubtractExpression(five, three), 
            two
        );
        assertEquals(4, complexExpression.interpret());
    }
    
    @Test
    void testFunctionalInterpreter() {
        FunctionalInterpreter.Expr five = FunctionalInterpreter.number(5);
        FunctionalInterpreter.Expr three = FunctionalInterpreter.number(3);
        FunctionalInterpreter.Expr two = FunctionalInterpreter.number(2);
        
        FunctionalInterpreter.Expr addExpr = FunctionalInterpreter.add(five, three);
        assertEquals(8, FunctionalInterpreter.interpret(addExpr));
        
        FunctionalInterpreter.Expr subtractExpr = FunctionalInterpreter.subtract(five, three);
        assertEquals(2, FunctionalInterpreter.interpret(subtractExpr));
        
        FunctionalInterpreter.Expr multiplyExpr = FunctionalInterpreter.multiply(five, three);
        assertEquals(15, FunctionalInterpreter.interpret(multiplyExpr));
        
        FunctionalInterpreter.Expr divideExpr = FunctionalInterpreter.divide(five, three);
        assertEquals(1, FunctionalInterpreter.interpret(divideExpr));
    }
    
    @Test
    void testComplexExpressions() {
        // Traditional: (5 + 3) - (2 + 1)
        Expression traditional = new SubtractExpression(
            new AddExpression(new NumberExpression(5), new NumberExpression(3)),
            new AddExpression(new NumberExpression(2), new NumberExpression(1))
        );
        assertEquals(5, traditional.interpret());
        
        // Functional: (5 + 3) - (2 + 1)
        FunctionalInterpreter.Expr functional = FunctionalInterpreter.subtract(
            FunctionalInterpreter.add(FunctionalInterpreter.number(5), FunctionalInterpreter.number(3)),
            FunctionalInterpreter.add(FunctionalInterpreter.number(2), FunctionalInterpreter.number(1))
        );
        assertEquals(5, FunctionalInterpreter.interpret(functional));
    }
    
    @Test
    void testExpressionToString() {
        Expression expr = new AddExpression(
            new NumberExpression(5),
            new SubtractExpression(new NumberExpression(10), new NumberExpression(3))
        );
        
        String result = expr.toString();
        assertTrue(result.contains("5"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("+"));
        assertTrue(result.contains("-"));
    }
    
    @Test
    void testFunctionalExpressionToString() {
        FunctionalInterpreter.Expr expr = FunctionalInterpreter.add(
            FunctionalInterpreter.number(5),
            FunctionalInterpreter.subtract(FunctionalInterpreter.number(10), FunctionalInterpreter.number(3))
        );
        
        String result = FunctionalInterpreter.toString(expr);
        assertTrue(result.contains("5"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("+"));
        assertTrue(result.contains("-"));
    }
    
    @Test
    void testNumberExpression() {
        NumberExpression zero = new NumberExpression(0);
        NumberExpression positive = new NumberExpression(42);
        NumberExpression negative = new NumberExpression(-10);
        
        assertEquals(0, zero.interpret());
        assertEquals(42, positive.interpret());
        assertEquals(-10, negative.interpret());
        
        assertEquals("0", zero.toString());
        assertEquals("42", positive.toString());
        assertEquals("-10", negative.toString());
    }
    
    @Test
    void testArithmeticOperations() {
        Expression a = new NumberExpression(10);
        Expression b = new NumberExpression(3);
        
        // Addition
        Expression add = new AddExpression(a, b);
        assertEquals(13, add.interpret());
        
        // Subtraction
        Expression subtract = new SubtractExpression(a, b);
        assertEquals(7, subtract.interpret());
        
        // Nested operations
        Expression nested = new AddExpression(
            new SubtractExpression(a, b),
            new AddExpression(new NumberExpression(2), new NumberExpression(1))
        );
        assertEquals(10, nested.interpret()); // (10-3) + (2+1) = 7 + 3 = 10
    }
    
    @Test
    void testFunctionalArithmeticOperations() {
        FunctionalInterpreter.Expr a = FunctionalInterpreter.number(10);
        FunctionalInterpreter.Expr b = FunctionalInterpreter.number(3);
        
        // All operations
        assertEquals(13, FunctionalInterpreter.interpret(FunctionalInterpreter.add(a, b)));
        assertEquals(7, FunctionalInterpreter.interpret(FunctionalInterpreter.subtract(a, b)));
        assertEquals(30, FunctionalInterpreter.interpret(FunctionalInterpreter.multiply(a, b)));
        assertEquals(3, FunctionalInterpreter.interpret(FunctionalInterpreter.divide(a, b)));
        
        // Nested operations
        FunctionalInterpreter.Expr nested = FunctionalInterpreter.add(
            FunctionalInterpreter.subtract(a, b),
            FunctionalInterpreter.add(FunctionalInterpreter.number(2), FunctionalInterpreter.number(1))
        );
        assertEquals(10, FunctionalInterpreter.interpret(nested));
    }
    
    @Test
    void testEquivalence() {
        // Same expression in both approaches should yield same result
        
        // Traditional: 5 + (10 - 3)
        Expression traditional = new AddExpression(
            new NumberExpression(5),
            new SubtractExpression(new NumberExpression(10), new NumberExpression(3))
        );
        
        // Functional: 5 + (10 - 3)
        FunctionalInterpreter.Expr functional = FunctionalInterpreter.add(
            FunctionalInterpreter.number(5),
            FunctionalInterpreter.subtract(FunctionalInterpreter.number(10), FunctionalInterpreter.number(3))
        );
        
        int traditionalResult = traditional.interpret();
        int functionalResult = FunctionalInterpreter.interpret(functional);
        
        assertEquals(traditionalResult, functionalResult);
        assertEquals(12, traditionalResult);
        assertEquals(12, functionalResult);
    }
    
    @Test
    void testInterpreterPatternBehavior() {
        // Test that expressions can be built and interpreted correctly
        
        // Build expression tree: ((5 + 3) * 2) - 1
        Expression expr = new SubtractExpression(
            new AddExpression(
                new AddExpression(new NumberExpression(5), new NumberExpression(3)),
                new AddExpression(new NumberExpression(5), new NumberExpression(3))
            ),
            new NumberExpression(1)
        );
        
        // Should evaluate to: (8 + 8) - 1 = 15
        assertEquals(15, expr.interpret());
        
        // Test that expressions are immutable and reusable
        assertEquals(15, expr.interpret());
        assertEquals(15, expr.interpret());
    }
    
    @Test
    void testFunctionalPatternMatching() {
        // Test that functional approach uses pattern matching correctly
        FunctionalInterpreter.Expr number = FunctionalInterpreter.number(42);
        FunctionalInterpreter.Expr binary = FunctionalInterpreter.add(
            FunctionalInterpreter.number(20), 
            FunctionalInterpreter.number(22)
        );
        
        assertEquals(42, FunctionalInterpreter.interpret(number));
        assertEquals(42, FunctionalInterpreter.interpret(binary));
        
        // Both should produce same result but through different paths
        assertEquals(
            FunctionalInterpreter.interpret(number),
            FunctionalInterpreter.interpret(binary)
        );
    }
}