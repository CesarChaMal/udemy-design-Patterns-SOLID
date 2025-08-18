package com.balazsholczer.fastlane;

import java.util.List;

/**
 * Fast Lane Reader Pattern: direct read access bypassing entity beans
 * 
 * Key Concepts:
 * - Provides direct read access to data bypassing heavy entity beans
 * - Uses lightweight read-only objects for query operations
 * - Optimizes performance for read-heavy operations
 * - Separates read operations from business logic operations
 * 
 * Structure:
 * - FastLaneReader: provides optimized read operations
 * - ReadOnlyData: lightweight data objects without business logic
 * - EntityBean: heavy objects with business logic (bypassed for reads)
 * - DAO: provides both entity and read-only data access
 * 
 * Benefits:
 * - Significantly improved read performance
 * - Reduced memory overhead for read operations
 * - Bypasses expensive entity bean lifecycle
 * - Optimized for reporting and query scenarios
 * 
 * Use Cases:
 * - Reporting systems with heavy read operations
 * - Data display and listing operations
 * - Search and filtering operations
 * - Dashboard and analytics queries
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Fast Lane Reader Pattern ===");
        System.out.println("Direct read access bypassing entity beans");
        System.out.println();
        
        ProductDAO productDAO = new ProductDAO();
        
        System.out.println("=== Traditional Entity Bean Approach ===");
        
        long startTime = System.currentTimeMillis();
        
        // Traditional approach - loading full entities (expensive)
        System.out.println("Loading individual entities:");
        ProductEntity entity1 = productDAO.findEntityById("P1");
        ProductEntity entity2 = productDAO.findEntityById("P2");
        
        System.out.println("Entity 1: " + entity1);
        System.out.println("Entity 2: " + entity2);
        
        // Business operations on entities
        entity1.updateStock(10);
        entity1.calculateDiscount();
        
        long entityTime = System.currentTimeMillis() - startTime;
        System.out.println("Traditional entity approach time: " + entityTime + "ms");
        
        System.out.println("\n=== Fast Lane Reader Approach ===");
        
        FastLaneReader fastLaneReader = new FastLaneReader(productDAO);
        
        startTime = System.currentTimeMillis();
        
        // Fast lane approach - lightweight read-only data
        System.out.println("Fast lane individual reads:");
        ProductReadOnlyData readOnly1 = fastLaneReader.getProductInfo("P1");
        ProductReadOnlyData readOnly2 = fastLaneReader.getProductInfo("P2");
        
        System.out.println("Read-only 1: " + readOnly1);
        System.out.println("Read-only 2: " + readOnly2);
        
        long fastLaneTime = System.currentTimeMillis() - startTime;
        System.out.println("Fast lane approach time: " + fastLaneTime + "ms");
        
        System.out.println("\n=== Bulk Operations Comparison ===");
        
        // Traditional bulk loading (very expensive)
        startTime = System.currentTimeMillis();
        System.out.println("Traditional: Loading all entities...");
        List<ProductEntity> allEntities = productDAO.findAllEntities();
        long bulkEntityTime = System.currentTimeMillis() - startTime;
        System.out.println("Loaded " + allEntities.size() + " entities in " + bulkEntityTime + "ms");
        
        // Fast lane bulk loading (much faster)
        startTime = System.currentTimeMillis();
        System.out.println("Fast lane: Loading all read-only data...");
        List<ProductReadOnlyData> allReadOnly = fastLaneReader.getAllProducts();
        long bulkFastLaneTime = System.currentTimeMillis() - startTime;
        System.out.println("Loaded " + allReadOnly.size() + " read-only objects in " + bulkFastLaneTime + "ms");
        
        System.out.println("Performance improvement: " + (bulkEntityTime - bulkFastLaneTime) + "ms faster");
        
        System.out.println("\n=== Fast Lane Query Operations ===");
        
        // Category-based queries
        List<ProductReadOnlyData> electronics = fastLaneReader.getProductsByCategory("Electronics");
        System.out.println("Electronics products: " + electronics.size());
        electronics.forEach(System.out::println);
        
        // Price range queries
        List<ProductReadOnlyData> midRange = fastLaneReader.getProductsInPriceRange(20.0, 100.0);
        System.out.println("\nProducts in $20-$100 range: " + midRange.size());
        midRange.forEach(System.out::println);
        
        // Summary operations
        FastLaneReader.ProductSummary summary = fastLaneReader.getProductSummary();
        System.out.println("\nProduct Summary: " + summary);
        
        System.out.println("\n=== Functional Fast Lane Reader ===");
        
        FunctionalFastLaneReader funcReader = new FunctionalFastLaneReader(productDAO);
        
        // Extract product names
        List<String> productNames = funcReader.readAndTransform(FunctionalFastLaneReader.nameExtractor());
        System.out.println("Product names: " + productNames);
        
        // Filter in-stock products
        List<ProductReadOnlyData> inStock = funcReader.readWithFilter(FunctionalFastLaneReader.inStockFilter());
        System.out.println("In-stock products: " + inStock.size());
        
        // Calculate average price
        Double avgPrice = funcReader.aggregateData(FunctionalFastLaneReader.averagePriceCalculator());
        System.out.println("Average price: $" + String.format("%.2f", avgPrice));
        
        // Calculate total stock
        Long totalStock = funcReader.aggregateData(FunctionalFastLaneReader.totalStockCalculator());
        System.out.println("Total stock: " + totalStock + " units");
        
        // Custom functional operations
        List<String> expensiveProducts = funcReader.readWithFilter(p -> p.getPrice().doubleValue() > 100)
                .stream()
                .map(ProductReadOnlyData::getName)
                .toList();
        System.out.println("Expensive products: " + expensiveProducts);
        
        System.out.println("\n=== Performance Analysis ===");
        System.out.println("Entity Bean Approach:");
        System.out.println("- Heavy objects with business logic");
        System.out.println("- Expensive lifecycle management");
        System.out.println("- Suitable for transactional operations");
        
        System.out.println("\nFast Lane Reader Approach:");
        System.out.println("- Lightweight read-only objects");
        System.out.println("- Direct database access");
        System.out.println("- Optimized for read-heavy scenarios");
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Significantly improved read performance");
        System.out.println("✅ Reduced memory overhead for read operations");
        System.out.println("✅ Bypassed expensive entity bean lifecycle");
        System.out.println("✅ Optimized bulk read operations");
        System.out.println("✅ Functional query and aggregation capabilities");
    }
}