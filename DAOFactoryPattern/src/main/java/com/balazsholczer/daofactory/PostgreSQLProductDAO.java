package com.balazsholczer.daofactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgreSQLProductDAO implements ProductDAO {
    
    private final Map<String, Product> database = new HashMap<>();
    
    @Override
    public void save(Product product) {
        System.out.println("PostgreSQLProductDAO: Saving product to PostgreSQL - " + product.getId());
        database.put(product.getId(), product);
    }
    
    @Override
    public Product findById(String id) {
        System.out.println("PostgreSQLProductDAO: Finding product in PostgreSQL - " + id);
        return database.get(id);
    }
    
    @Override
    public List<Product> findAll() {
        System.out.println("PostgreSQLProductDAO: Finding all products in PostgreSQL");
        return new ArrayList<>(database.values());
    }
    
    @Override
    public void delete(String id) {
        System.out.println("PostgreSQLProductDAO: Deleting product from PostgreSQL - " + id);
        database.remove(id);
    }
}