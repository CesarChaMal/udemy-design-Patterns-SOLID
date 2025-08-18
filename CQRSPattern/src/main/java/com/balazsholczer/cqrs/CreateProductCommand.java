package com.balazsholczer.cqrs;

import java.math.BigDecimal;

public class CreateProductCommand implements Command {
    private final String productId;
    private final String name;
    private final BigDecimal price;
    
    public CreateProductCommand(String productId, String name, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
    
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    
    @Override
    public String getCommandType() { return "CreateProduct"; }
}