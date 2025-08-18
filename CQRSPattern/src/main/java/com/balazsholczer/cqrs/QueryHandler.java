package com.balazsholczer.cqrs;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class QueryHandler {
    private final Map<String, ProductView> readStore = new HashMap<>();
    
    public void updateReadModel(String productId, Map<String, Object> productData) {
        System.out.println("QueryHandler: Updating read model for " + productId);
        
        // Create optimized read model
        ProductView view = new ProductView(
            (String) productData.get("id"),
            (String) productData.get("name"),
            (BigDecimal) productData.get("price"),
            (String) productData.get("category")
        );
        
        readStore.put(productId, view);
        System.out.println("QueryHandler: Read model updated");
    }
    
    public ProductView handle(GetProductQuery query) {
        System.out.println("QueryHandler: Querying product " + query.getProductId());
        return readStore.get(query.getProductId());
    }
}