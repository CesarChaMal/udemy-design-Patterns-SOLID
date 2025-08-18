package com.balazsholczer.cqrs;

public class GetProductQuery implements Query<ProductView> {
    private final String productId;
    
    public GetProductQuery(String productId) {
        this.productId = productId;
    }
    
    public String getProductId() { return productId; }
    
    @Override
    public String getQueryType() { return "GetProduct"; }
}