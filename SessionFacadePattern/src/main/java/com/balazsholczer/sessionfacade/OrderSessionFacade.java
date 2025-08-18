package com.balazsholczer.sessionfacade;

import java.util.List;

public class OrderSessionFacade {
    
    private final CustomerService customerService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    
    public OrderSessionFacade() {
        this.customerService = new CustomerService();
        this.orderService = new OrderService();
        this.paymentService = new PaymentService();
    }
    
    // Coarse-grained method that coordinates multiple fine-grained services
    public String processCompleteOrder(String customerId, List<String> items) {
        System.out.println("OrderSessionFacade: Processing complete order workflow");
        
        // Step 1: Validate customer
        Customer customer = customerService.getCustomer(customerId);
        if (customer == null) {
            return "Order failed: Customer not found";
        }
        
        // Step 2: Create order
        Order order = orderService.createOrder(customerId, items);
        
        // Step 3: Process payment
        boolean paymentSuccess = paymentService.processPayment(order.getOrderId(), order.getTotal());
        
        if (paymentSuccess) {
            System.out.println("OrderSessionFacade: Order completed successfully");
            return "Order " + order.getOrderId() + " completed successfully";
        } else {
            System.out.println("OrderSessionFacade: Payment failed, order cancelled");
            return "Order failed: Payment processing error";
        }
    }
    
    // Another coarse-grained method
    public String cancelOrder(String orderId) {
        System.out.println("OrderSessionFacade: Cancelling order " + orderId);
        
        Order order = orderService.getOrder(orderId);
        paymentService.refundPayment(orderId, order.getTotal());
        
        return "Order " + orderId + " cancelled and refunded";
    }
}