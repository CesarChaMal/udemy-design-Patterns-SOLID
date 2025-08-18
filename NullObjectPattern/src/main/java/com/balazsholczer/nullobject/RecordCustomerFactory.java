package com.balazsholczer.nullobject;

public class RecordCustomerFactory {
    
    private final Database database = new Database();
    
    public CustomerRecord getCustomer(String name) {
        return database.existCustomer(name) 
            ? CustomerRecord.of(name, true)
            : CustomerRecord.notFound();
    }
}