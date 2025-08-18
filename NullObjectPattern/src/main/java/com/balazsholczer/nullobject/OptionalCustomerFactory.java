package com.balazsholczer.nullobject;

import java.util.Optional;

public class OptionalCustomerFactory {
    
    private final Database database = new Database();
    
    public Optional<String> getCustomer(String name) {
        return database.existCustomer(name) ? Optional.of(name) : Optional.empty();
    }
    
    public String getCustomerName(String name) {
        return getCustomer(name).orElse("No person with the given name in the database...");
    }
}