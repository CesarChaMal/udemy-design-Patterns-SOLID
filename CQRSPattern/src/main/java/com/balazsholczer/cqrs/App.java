package com.balazsholczer.cqrs;

import java.math.BigDecimal;
import java.util.Map;

/**
 * CQRS Pattern: Command Query Responsibility Segregation
 * 
 * Key Concepts:
 * - Separates read and write operations
 * - Different models for commands and queries
 * - Optimized data structures for each operation type
 * - Eventual consistency between read and write models
 * 
 * Benefits:
 * - Scalable read and write operations
 * - Optimized data models for specific use cases
 * - Independent scaling of read/write sides
 * - Complex query support without affecting writes
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== CQRS Pattern ===");
        System.out.println("Command Query Responsibility Segregation");
        System.out.println();
        
        CommandHandler commandHandler = new CommandHandler();
        QueryHandler queryHandler = new QueryHandler();
        
        // Execute command (write operation)
        CreateProductCommand command = new CreateProductCommand("PROD-001", "Laptop", BigDecimal.valueOf(999.99));
        commandHandler.handle(command);
        
        // Sync read model (in real system this would be async)
        @SuppressWarnings("unchecked")
        Map<String, Object> productData = (Map<String, Object>) commandHandler.getWriteStore().get(command.getProductId());
        queryHandler.updateReadModel(command.getProductId(), productData);
        
        // Execute query (read operation)
        GetProductQuery query = new GetProductQuery("PROD-001");
        ProductView product = queryHandler.handle(query);
        
        System.out.println("Query result: " + product);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Separated read and write operations");
        System.out.println("✅ Optimized models for different use cases");
        System.out.println("✅ Independent command and query handling");
        System.out.println("✅ Scalable architecture design");
    }
}