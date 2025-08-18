package com.balazsholczer.compositeentity;

public class CompositeEntity {
    private CoarseGrainedObject coarseGrainedObject;
    
    public CompositeEntity(String customerId, String name, String email) {
        this.coarseGrainedObject = new CoarseGrainedObject(customerId, name, email);
        System.out.println("CompositeEntity: Created composite entity for " + customerId);
    }
    
    public void setData(String name, String email) {
        coarseGrainedObject.updateCustomerData(name, email);
        System.out.println("CompositeEntity: Updated data via composite entity");
    }
    
    public void setAddress(String street, String city, String zipCode) {
        Address address = new Address(street, city, zipCode);
        coarseGrainedObject.setAddress(address);
        System.out.println("CompositeEntity: Set address via composite entity");
    }
    
    public void addPhone(String type, String number) {
        Phone phone = new Phone(type, number);
        coarseGrainedObject.addPhone(phone);
        System.out.println("CompositeEntity: Added phone via composite entity");
    }
    
    public String[] getData() {
        return new String[] {
            coarseGrainedObject.getCustomerId(),
            coarseGrainedObject.getName(),
            coarseGrainedObject.getEmail()
        };
    }
    
    public String getAddressInfo() {
        Address address = coarseGrainedObject.getAddress();
        return address != null ? address.toString() : "No address";
    }
    
    public String getPhoneInfo() {
        var phones = coarseGrainedObject.getPhones();
        return "Phones: " + phones.size() + " - " + phones.toString();
    }
    
    @Override
    public String toString() {
        return "CompositeEntity{customer=" + coarseGrainedObject + "}";
    }
}