package com.balazsholczer.compositeentity;

import java.util.ArrayList;
import java.util.List;

public class CoarseGrainedObject {
    private String customerId;
    private String name;
    private String email;
    private Address address;
    private List<Phone> phones;
    
    public CoarseGrainedObject(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phones = new ArrayList<>();
        System.out.println("CoarseGrainedObject: Created customer " + customerId);
    }
    
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Address getAddress() { return address; }
    public void setAddress(Address address) { 
        this.address = address;
        System.out.println("CoarseGrainedObject: Set address for " + customerId);
    }
    
    public List<Phone> getPhones() { return new ArrayList<>(phones); }
    public void addPhone(Phone phone) { 
        phones.add(phone);
        System.out.println("CoarseGrainedObject: Added phone for " + customerId);
    }
    
    public void removePhone(Phone phone) { 
        phones.remove(phone);
        System.out.println("CoarseGrainedObject: Removed phone for " + customerId);
    }
    
    public void updateCustomerData(String name, String email) {
        this.name = name;
        this.email = email;
        System.out.println("CoarseGrainedObject: Updated customer data for " + customerId);
    }
    
    @Override
    public String toString() {
        return "Customer{id='" + customerId + "', name='" + name + "', email='" + email + 
               "', address=" + address + ", phones=" + phones.size() + "}";
    }
}