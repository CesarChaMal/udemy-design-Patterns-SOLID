package com.balazsholczer.cqrs;

import java.math.BigDecimal;

public class ProductView {
    private final String id;
    private final String name;
    private final BigDecimal price;
    private final String category;
    
    public ProductView(String id, String name, BigDecimal price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    
    @Override
    public String toString() {
        return "ProductView{id='" + id + "', name='" + name + "', price=" + price + ", category='" + category + "'}";
    }
}