package com.balazsholczer.fastlane;

import java.math.BigDecimal;

public class ProductReadOnlyData {
    private final String id;
    private final String name;
    private final BigDecimal price;
    private final String category;
    private final int stock;
    
    public ProductReadOnlyData(String id, String name, BigDecimal price, String category, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        System.out.println("ProductReadOnlyData: Created lightweight read-only data for " + id);
    }
    
    // Only getters - no business logic
    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public int getStock() { return stock; }
    
    @Override
    public String toString() {
        return "ProductReadOnlyData{id='" + id + "', name='" + name + "', price=" + price + 
               ", category='" + category + "', stock=" + stock + "}";
    }
}