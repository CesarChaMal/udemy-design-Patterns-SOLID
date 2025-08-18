package com.balazsholczer.interpreter;

/**
 * Interpreter Pattern: defines grammar and interprets sentences in a language
 * 
 * Key Concepts:
 * - Grammar: defines language rules and structure
 * - Terminal Expression: leaf nodes (numbers, variables)
 * - Non-terminal Expression: composite nodes (operators, functions)
 * - Context: contains global information for interpretation
 * - Abstract Syntax Tree (AST): represents parsed expressions
 * 
 * Structure:
 * - AbstractExpression: declares interpret operation
 * - TerminalExpression: implements interpret for terminal symbols
 * - NonterminalExpression: implements interpret for grammar rules
 * - Context: contains information global to interpreter
 * - Client: builds AST and invokes interpret
 * 
 * Benefits:
 * - Easy to change and extend grammar
 * - Complex grammars become maintainable
 * - Adding new ways to interpret expressions is easy
 * - Reusable for different contexts
 * 
 * Use Cases:
 * - Mathematical expression evaluators
 * - SQL query processors
 * - Configuration file parsers
 * - Domain-specific languages (DSL)
 * - Rule engines and business logic
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Interpreter Pattern ===");
        
        // Build Abstract Syntax Tree: (5 + 3) - 2
        Expression five = new NumberExpression(5);
        Expression three = new NumberExpression(3);
        Expression two = new NumberExpression(2);
        
        Expression addition = new AddExpression(five, three);
        Expression subtraction = new SubtractExpression(addition, two);
        
        // Interpret the expression
        System.out.println("Expression: " + subtraction);
        System.out.println("Result: " + subtraction.interpret());
        
        // Parse and interpret postfix notation
        System.out.println("\nPostfix Parsing:");
        Expression parsed = ExpressionParser.parse("10 5 + 3 -");
        System.out.println("Parsed: " + parsed);
        System.out.println("Result: " + parsed.interpret());
        
        System.out.println("\n=== Run ModernInterpreterDemo for all approaches ===");
    }
}