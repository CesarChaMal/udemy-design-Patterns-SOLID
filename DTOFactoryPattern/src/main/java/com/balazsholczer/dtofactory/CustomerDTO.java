package com.balazsholczer.dtofactory;

public class CustomerDTO {
    private String id;
    private String name;
    private String email;
    
    public CustomerDTO(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    
    @Override
    public String toString() {
        return "CustomerDTO{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }
}