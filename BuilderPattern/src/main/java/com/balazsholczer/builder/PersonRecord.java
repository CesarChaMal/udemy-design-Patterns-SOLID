package com.balazsholczer.builder;

public record PersonRecord(String name, String email, String address, int age, String nameOfMother) {
    
    public static PersonRecord of(String name, String email) {
        return new PersonRecord(name, email, null, 0, null);
    }
    
    public PersonRecord withAddress(String address) {
        return new PersonRecord(name, email, address, age, nameOfMother);
    }
    
    public PersonRecord withAge(int age) {
        return new PersonRecord(name, email, address, age, nameOfMother);
    }
    
    public PersonRecord withMother(String nameOfMother) {
        return new PersonRecord(name, email, address, age, nameOfMother);
    }
}