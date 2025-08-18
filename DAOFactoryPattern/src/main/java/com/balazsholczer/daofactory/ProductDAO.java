package com.balazsholczer.daofactory;

import java.util.List;

public interface ProductDAO {
    void save(Product product);
    Product findById(String id);
    List<Product> findAll();
    void delete(String id);
}