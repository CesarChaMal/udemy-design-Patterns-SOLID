package com.balazsholczer.dtofactory;

import java.math.BigDecimal;
import java.util.Map;

public abstract class DTOFactory {
    
    public static DTOFactory getInstance(String type) {
        switch (type.toLowerCase()) {
            case "customer":
                return new CustomerDTOFactory();
            case "order":
                return new OrderDTOFactory();
            default:
                throw new IllegalArgumentException("Unknown DTO type: " + type);
        }
    }
    
    public abstract Object createDTO(Map<String, Object> data);
    
    private static class CustomerDTOFactory extends DTOFactory {
        @Override
        public CustomerDTO createDTO(Map<String, Object> data) {
            System.out.println("CustomerDTOFactory: Creating CustomerDTO");
            return new CustomerDTO(
                (String) data.get("id"),
                (String) data.get("name"),
                (String) data.get("email")
            );
        }
    }
    
    private static class OrderDTOFactory extends DTOFactory {
        @Override
        public OrderDTO createDTO(Map<String, Object> data) {
            System.out.println("OrderDTOFactory: Creating OrderDTO");
            return new OrderDTO(
                (String) data.get("orderId"),
                (String) data.get("customerId"),
                (BigDecimal) data.get("total")
            );
        }
    }
}