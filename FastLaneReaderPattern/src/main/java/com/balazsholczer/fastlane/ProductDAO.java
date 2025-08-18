package com.balazsholczer.fastlane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    
    private final Map<String, ProductEntity> entityStore = new HashMap<>();
    
    public ProductDAO() {
        // Initialize with sample data
        entityStore.put("P1", new ProductEntity("P1", "Laptop", BigDecimal.valueOf(999.99), "Electronics", 50));
        entityStore.put("P2", new ProductEntity("P2", "Mouse", BigDecimal.valueOf(29.99), "Electronics", 100));
        entityStore.put("P3", new ProductEntity("P3", "Book", BigDecimal.valueOf(19.99), "Books", 200));
        entityStore.put("P4", new ProductEntity("P4", "Chair", BigDecimal.valueOf(149.99), "Furniture", 25));
        System.out.println("ProductDAO: Initialized with sample data");
    }
    
    // Traditional approach - returns full entity (expensive)
    public ProductEntity findEntityById(String id) {
        System.out.println("ProductDAO: Loading full entity for " + id + " (expensive operation)");
        // Simulate expensive entity loading
        try {
            Thread.sleep(100); // Simulate database/network overhead
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return entityStore.get(id);
    }
    
    // Traditional approach - returns all entities (very expensive)
    public List<ProductEntity> findAllEntities() {
        System.out.println("ProductDAO: Loading all entities (very expensive operation)");
        // Simulate expensive bulk loading
        try {
            Thread.sleep(200 * entityStore.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ArrayList<>(entityStore.values());
    }
    
    // Fast Lane Reader - returns lightweight read-only data
    public ProductReadOnlyData findReadOnlyById(String id) {
        System.out.println("ProductDAO: Fast lane read for " + id + " (lightweight operation)");
        // Simulate fast direct database read
        try {
            Thread.sleep(10); // Much faster than entity loading
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        ProductEntity entity = entityStore.get(id);
        if (entity != null) {
            return new ProductReadOnlyData(entity.getId(), entity.getName(), 
                                         entity.getPrice(), entity.getCategory(), entity.getStock());
        }
        return null;
    }
    
    // Fast Lane Reader - returns all read-only data
    public List<ProductReadOnlyData> findAllReadOnly() {
        System.out.println("ProductDAO: Fast lane read for all products (lightweight bulk operation)");
        // Simulate fast bulk read
        try {
            Thread.sleep(20); // Much faster than loading all entities
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<ProductReadOnlyData> readOnlyList = new ArrayList<>();
        for (ProductEntity entity : entityStore.values()) {
            readOnlyList.add(new ProductReadOnlyData(entity.getId(), entity.getName(),
                                                   entity.getPrice(), entity.getCategory(), entity.getStock()));
        }
        return readOnlyList;
    }
    
    // Fast Lane Reader - category-specific read
    public List<ProductReadOnlyData> findReadOnlyByCategory(String category) {
        System.out.println("ProductDAO: Fast lane read by category " + category);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<ProductReadOnlyData> result = new ArrayList<>();
        for (ProductEntity entity : entityStore.values()) {
            if (category.equals(entity.getCategory())) {
                result.add(new ProductReadOnlyData(entity.getId(), entity.getName(),
                                                 entity.getPrice(), entity.getCategory(), entity.getStock()));
            }
        }
        return result;
    }
}