package com.balazsholczer.nullobject;

import java.util.function.Supplier;

public class FunctionalCustomerFactory {
    
    private final Database database = new Database();
    private static final Supplier<String> NULL_CUSTOMER = () -> "No person with the given name in the database...";
    
    public Supplier<String> getCustomer(String name) {
        return database.existCustomer(name) ? () -> name : NULL_CUSTOMER;
    }
    
    public String getCustomerName(String name) {
        return getCustomer(name).get();
    }
}