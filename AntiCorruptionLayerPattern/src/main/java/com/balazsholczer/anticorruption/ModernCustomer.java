package com.balazsholczer.anticorruption;

public class ModernCustomer {
    private final String customerId;
    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    
    public ModernCustomer(String customerId, String name, String email, String phone, CustomerStatus status) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }
    
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public CustomerStatus getStatus() { return status; }
    
    @Override
    public String toString() {
        return "ModernCustomer{customerId='" + customerId + "', name='" + name + 
               "', email='" + email + "', phone='" + phone + "', status=" + status + "}";
    }
    
    public enum CustomerStatus {
        ACTIVE, INACTIVE
    }
}