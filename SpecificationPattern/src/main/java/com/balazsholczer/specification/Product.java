package com.balazsholczer.specification;

import java.math.BigDecimal;

public class Product {
    private String name;
    private BigDecimal price;
    private String category;
    private boolean inStock;
    
    public Product(String name, BigDecimal price, String category, boolean inStock) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.inStock = inStock;
    }
    
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isInStock() { return inStock; }
    
    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + ", category='" + category + "', inStock=" + inStock + "}";
    }
}