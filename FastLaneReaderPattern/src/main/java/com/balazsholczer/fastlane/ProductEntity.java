package com.balazsholczer.fastlane;

import java.math.BigDecimal;

public class ProductEntity {
    private String id;
    private String name;
    private BigDecimal price;
    private String category;
    private int stock;
    
    public ProductEntity(String id, String name, BigDecimal price, String category, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        System.out.println("ProductEntity: Created entity " + id + " (heavy object with business logic)");
    }
    
    // Business methods (expensive operations)
    public void updateStock(int quantity) {
        System.out.println("ProductEntity: Updating stock with business rules for " + id);
        // Simulate complex business logic
        this.stock += quantity;
    }
    
    public BigDecimal calculateDiscount() {
        System.out.println("ProductEntity: Calculating discount with complex rules for " + id);
        // Simulate expensive calculation
        return price.multiply(BigDecimal.valueOf(0.1));
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public int getStock() { return stock; }
    
    @Override
    public String toString() {
        return "ProductEntity{id='" + id + "', name='" + name + "', price=" + price + 
               ", category='" + category + "', stock=" + stock + "}";
    }
}