package com.balazsholczer.unitofwork;

public class Customer extends Entity {
    private String name;
    private String email;
    
    public Customer(String id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }
    
    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name; 
        markDirty();
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { 
        this.email = email; 
        markDirty();
    }
    
    @Override
    public String toString() {
        return "Customer{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }
}