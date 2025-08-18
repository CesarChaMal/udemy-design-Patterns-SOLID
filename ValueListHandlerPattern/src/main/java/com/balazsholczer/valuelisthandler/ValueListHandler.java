package com.balazsholczer.valuelisthandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ValueListHandler {
    private final List<Product> allProducts;
    
    public ValueListHandler() {
        // Initialize with large dataset
        allProducts = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            allProducts.add(new Product("PROD-" + i, "Product " + i, BigDecimal.valueOf(i * 10)));
        }
        System.out.println("ValueListHandler: Initialized with " + allProducts.size() + " products");
    }
    
    public ValueList<Product> getProducts(int page, int pageSize) {
        System.out.println("ValueListHandler: Fetching page " + page + " with size " + pageSize);
        
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allProducts.size());
        
        if (startIndex >= allProducts.size()) {
            return new ValueList<>(new ArrayList<>(), allProducts.size(), pageSize, page);
        }
        
        List<Product> pageData = allProducts.subList(startIndex, endIndex);
        System.out.println("ValueListHandler: Returning " + pageData.size() + " products");
        
        return new ValueList<>(new ArrayList<>(pageData), allProducts.size(), pageSize, page);
    }
    
    public ValueList<Product> searchProducts(String nameFilter, int page, int pageSize) {
        System.out.println("ValueListHandler: Searching for '" + nameFilter + "' page " + page);
        
        List<Product> filtered = allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(nameFilter.toLowerCase()))
                .toList();
        
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, filtered.size());
        
        if (startIndex >= filtered.size()) {
            return new ValueList<>(new ArrayList<>(), filtered.size(), pageSize, page);
        }
        
        List<Product> pageData = filtered.subList(startIndex, endIndex);
        return new ValueList<>(new ArrayList<>(pageData), filtered.size(), pageSize, page);
    }
}