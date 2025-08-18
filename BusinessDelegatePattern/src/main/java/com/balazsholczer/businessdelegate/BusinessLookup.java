package com.balazsholczer.businessdelegate;

public class BusinessLookup {
    
    public BusinessService getBusinessService(String serviceType) {
        return switch (serviceType.toLowerCase()) {
            case "order" -> new OrderService();
            case "payment" -> new PaymentService();
            default -> throw new IllegalArgumentException("Unknown service type: " + serviceType);
        };
    }
}