package com.balazsholczer.valuelisthandler;

/**
 * Value List Handler Pattern: handles large result sets efficiently
 * 
 * Key Concepts:
 * - Manages large collections with pagination
 * - Provides efficient data access for large datasets
 * - Supports filtering and searching
 * - Optimizes memory usage and performance
 * 
 * Benefits:
 * - Efficient handling of large result sets
 * - Reduced memory consumption
 * - Improved application performance
 * - Scalable data access patterns
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Value List Handler Pattern ===");
        System.out.println("Efficient large result set handling");
        System.out.println();
        
        ValueListHandler handler = new ValueListHandler();
        
        // Get first page
        ValueList<Product> page1 = handler.getProducts(1, 10);
        System.out.println("Page 1 results:");
        page1.getData().forEach(System.out::println);
        System.out.println("Total: " + page1.getTotalCount() + ", Pages: " + page1.getTotalPages());
        
        System.out.println("\n--- Navigation ---");
        System.out.println("Has next: " + page1.hasNext());
        System.out.println("Has previous: " + page1.hasPrevious());
        
        // Get middle page
        ValueList<Product> page50 = handler.getProducts(50, 10);
        System.out.println("\nPage 50 results (showing first 3):");
        page50.getData().stream().limit(3).forEach(System.out::println);
        
        // Search with pagination
        System.out.println("\n--- Search Results ---");
        ValueList<Product> searchResults = handler.searchProducts("Product 1", 1, 5);
        System.out.println("Search results for 'Product 1':");
        searchResults.getData().forEach(System.out::println);
        System.out.println("Found: " + searchResults.getTotalCount() + " matches");
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Efficient large dataset handling");
        System.out.println("✅ Memory-optimized pagination");
        System.out.println("✅ Scalable search and filtering");
        System.out.println("✅ Performance optimization for large collections");
    }
}