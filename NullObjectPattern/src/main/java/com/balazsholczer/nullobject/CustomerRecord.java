package com.balazsholczer.nullobject;

public record CustomerRecord(String name, boolean exists) {
    
    public static CustomerRecord of(String name, boolean exists) {
        return new CustomerRecord(name, exists);
    }
    
    public static CustomerRecord notFound() {
        return new CustomerRecord("No person with the given name in the database...", false);
    }
    
    public String getPerson() {
        return name;
    }
    
    public boolean isNull() {
        return !exists;
    }
}