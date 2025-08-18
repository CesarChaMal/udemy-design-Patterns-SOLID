package com.balazsholczer.daofactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLProductDAO implements ProductDAO {
    
    private final Map<String, Product> database = new HashMap<>();
    
    @Override
    public void save(Product product) {
        System.out.println("MySQLProductDAO: Saving product to MySQL - " + product.getId());
        database.put(product.getId(), product);
    }
    
    @Override
    public Product findById(String id) {
        System.out.println("MySQLProductDAO: Finding product in MySQL - " + id);
        return database.get(id);
    }
    
    @Override
    public List<Product> findAll() {
        System.out.println("MySQLProductDAO: Finding all products in MySQL");
        return new ArrayList<>(database.values());
    }
    
    @Override
    public void delete(String id) {
        System.out.println("MySQLProductDAO: Deleting product from MySQL - " + id);
        database.remove(id);
    }
}