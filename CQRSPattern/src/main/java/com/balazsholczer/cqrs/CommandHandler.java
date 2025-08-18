package com.balazsholczer.cqrs;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Object> writeStore = new HashMap<>();
    
    public void handle(CreateProductCommand command) {
        System.out.println("CommandHandler: Creating product " + command.getProductId());
        
        // Store in write model (normalized for writes)
        Map<String, Object> product = new HashMap<>();
        product.put("id", command.getProductId());
        product.put("name", command.getName());
        product.put("price", command.getPrice());
        product.put("category", "Electronics"); // Business logic
        
        writeStore.put(command.getProductId(), product);
        System.out.println("CommandHandler: Product created successfully");
    }
    
    public Map<String, Object> getWriteStore() {
        return writeStore;
    }
}