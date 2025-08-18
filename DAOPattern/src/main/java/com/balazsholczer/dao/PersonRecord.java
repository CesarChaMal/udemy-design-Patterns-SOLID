package com.balazsholczer.dao;

public record PersonRecord(String name, int age, String gender, String address) {
    
    public PersonRecord(String name, int age) {
        this(name, age, null, null);
    }
    
    public PersonRecord withGender(String gender) {
        return new PersonRecord(name, age, gender, address);
    }
    
    public PersonRecord withAddress(String address) {
        return new PersonRecord(name, age, gender, address);
    }
}