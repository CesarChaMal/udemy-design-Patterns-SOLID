package com.balazsholczer.specification;

import java.math.BigDecimal;

public class ProductSpecifications {
    
    public static Specification<Product> inStock() {
        return product -> product.isInStock();
    }
    
    public static Specification<Product> priceGreaterThan(BigDecimal price) {
        return product -> product.getPrice().compareTo(price) > 0;
    }
    
    public static Specification<Product> priceLessThan(BigDecimal price) {
        return product -> product.getPrice().compareTo(price) < 0;
    }
    
    public static Specification<Product> inCategory(String category) {
        return product -> category.equals(product.getCategory());
    }
    
    public static Specification<Product> nameContains(String text) {
        return product -> product.getName().toLowerCase().contains(text.toLowerCase());
    }
    
    // Composite specifications
    public static Specification<Product> affordableAndInStock(BigDecimal maxPrice) {
        return priceLessThan(maxPrice).and(inStock());
    }
    
    public static Specification<Product> expensiveElectronics() {
        return inCategory("Electronics").and(priceGreaterThan(BigDecimal.valueOf(500)));
    }
}