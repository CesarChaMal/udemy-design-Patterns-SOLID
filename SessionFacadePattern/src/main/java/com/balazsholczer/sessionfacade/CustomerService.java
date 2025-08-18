package com.balazsholczer.sessionfacade;

public class CustomerService {
    
    public Customer getCustomer(String customerId) {
        System.out.println("CustomerService: Retrieving customer " + customerId);
        return new Customer(customerId, "John Doe", "john@example.com");
    }
    
    public void updateCustomer(Customer customer) {
        System.out.println("CustomerService: Updating customer " + customer.getCustomerId());
    }
}