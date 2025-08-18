package com.balazsholczer.assembler;

import java.util.List;

public class CustomerTO {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private List<OrderTO> orders;
    private AccountTO account;
    
    public CustomerTO() {}
    
    public CustomerTO(String customerId, String name, String email, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
    }
    
    // Getters and setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public List<OrderTO> getOrders() { return orders; }
    public void setOrders(List<OrderTO> orders) { this.orders = orders; }
    
    public AccountTO getAccount() { return account; }
    public void setAccount(AccountTO account) { this.account = account; }
    
    @Override
    public String toString() {
        return "CustomerTO{id='" + customerId + "', name='" + name + "', orders=" + 
               (orders != null ? orders.size() : 0) + ", account=" + (account != null) + "}";
    }
}