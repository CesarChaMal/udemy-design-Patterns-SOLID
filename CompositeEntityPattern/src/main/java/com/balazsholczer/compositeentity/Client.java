package com.balazsholczer.compositeentity;

public class Client {
    private CompositeEntity compositeEntity;
    
    public void createCustomer(String customerId, String name, String email) {
        compositeEntity = new CompositeEntity(customerId, name, email);
        System.out.println("Client: Created customer composite entity");
    }
    
    public void updateCustomer(String name, String email) {
        if (compositeEntity != null) {
            compositeEntity.setData(name, email);
            System.out.println("Client: Updated customer via composite entity");
        }
    }
    
    public void setCustomerAddress(String street, String city, String zipCode) {
        if (compositeEntity != null) {
            compositeEntity.setAddress(street, city, zipCode);
            System.out.println("Client: Set customer address via composite entity");
        }
    }
    
    public void addCustomerPhone(String type, String number) {
        if (compositeEntity != null) {
            compositeEntity.addPhone(type, number);
            System.out.println("Client: Added customer phone via composite entity");
        }
    }
    
    public void printCustomerData() {
        if (compositeEntity != null) {
            String[] data = compositeEntity.getData();
            System.out.println("Client: Customer Data - ID: " + data[0] + ", Name: " + data[1] + ", Email: " + data[2]);
            System.out.println("Client: " + compositeEntity.getAddressInfo());
            System.out.println("Client: " + compositeEntity.getPhoneInfo());
        }
    }
}