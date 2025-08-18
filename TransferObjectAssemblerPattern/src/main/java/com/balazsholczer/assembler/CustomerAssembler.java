package com.balazsholczer.assembler;

import java.util.List;

public class CustomerAssembler {
    
    private final CustomerDAO customerDAO;
    private final OrderDAO orderDAO;
    private final AccountDAO accountDAO;
    
    public CustomerAssembler() {
        this.customerDAO = new CustomerDAO();
        this.orderDAO = new OrderDAO();
        this.accountDAO = new AccountDAO();
    }
    
    public CustomerTO assembleCustomer(String customerId) {
        System.out.println("CustomerAssembler: Assembling complete customer data for " + customerId);
        
        // Get base customer data
        CustomerTO customer = customerDAO.findById(customerId);
        if (customer == null) {
            return null;
        }
        
        // Assemble orders
        List<OrderTO> orders = orderDAO.findByCustomerId(customerId);
        customer.setOrders(orders);
        
        // Assemble account
        AccountTO account = accountDAO.findByCustomerId(customerId);
        customer.setAccount(account);
        
        System.out.println("CustomerAssembler: Assembly completed");
        return customer;
    }
    
    public CustomerTO assembleCustomerBasic(String customerId) {
        System.out.println("CustomerAssembler: Assembling basic customer data for " + customerId);
        return customerDAO.findById(customerId);
    }
    
    public CustomerTO assembleCustomerWithOrders(String customerId) {
        System.out.println("CustomerAssembler: Assembling customer with orders for " + customerId);
        
        CustomerTO customer = customerDAO.findById(customerId);
        if (customer != null) {
            List<OrderTO> orders = orderDAO.findByCustomerId(customerId);
            customer.setOrders(orders);
        }
        
        return customer;
    }
}