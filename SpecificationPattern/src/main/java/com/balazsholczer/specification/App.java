package com.balazsholczer.specification;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Specification Pattern: business rules as objects
 * 
 * Key Concepts:
 * - Encapsulates business rules in reusable objects
 * - Enables composition of complex business logic
 * - Separates business rules from application logic
 * - Provides fluent interface for rule combination
 * 
 * Benefits:
 * - Reusable business rules
 * - Composable complex logic
 * - Testable rule objects
 * - Domain-driven design compliance
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Specification Pattern ===");
        System.out.println("Business rules as objects");
        System.out.println();
        
        List<Product> products = Arrays.asList(
            new Product("Laptop", BigDecimal.valueOf(999.99), "Electronics", true),
            new Product("Mouse", BigDecimal.valueOf(29.99), "Electronics", true),
            new Product("Book", BigDecimal.valueOf(19.99), "Books", false),
            new Product("Phone", BigDecimal.valueOf(699.99), "Electronics", true),
            new Product("Desk", BigDecimal.valueOf(299.99), "Furniture", false)
        );
        
        // Simple specifications
        List<Product> inStockProducts = products.stream()
                .filter(ProductSpecifications.inStock()::isSatisfiedBy)
                .collect(Collectors.toList());
        System.out.println("In stock products: " + inStockProducts.size());
        
        // Composite specifications
        Specification<Product> affordableSpec = ProductSpecifications.affordableAndInStock(BigDecimal.valueOf(100));
        List<Product> affordable = products.stream()
                .filter(affordableSpec::isSatisfiedBy)
                .collect(Collectors.toList());
        System.out.println("Affordable and in stock: " + affordable);
        
        // Complex combinations
        Specification<Product> complexSpec = ProductSpecifications.inCategory("Electronics")
                .and(ProductSpecifications.inStock())
                .and(ProductSpecifications.priceLessThan(BigDecimal.valueOf(800)));
        
        List<Product> filtered = products.stream()
                .filter(complexSpec::isSatisfiedBy)
                .collect(Collectors.toList());
        System.out.println("Electronics under $800 in stock: " + filtered);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Reusable business rules");
        System.out.println("✅ Composable complex logic");
        System.out.println("✅ Testable rule objects");
        System.out.println("✅ Fluent interface for combinations");
    }
}