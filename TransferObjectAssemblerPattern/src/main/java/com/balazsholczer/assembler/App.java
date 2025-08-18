package com.balazsholczer.assembler;

import java.util.function.Function;

/**
 * Transfer Object Assembler Pattern: builds complex transfer objects from multiple sources
 * 
 * Key Concepts:
 * - Assembles complex transfer objects from multiple data sources
 * - Reduces network calls by combining related data
 * - Provides different assembly strategies for different use cases
 * - Separates data assembly logic from business logic
 * 
 * Structure:
 * - TransferObject: complex object containing data from multiple sources
 * - Assembler: builds transfer objects by coordinating multiple DAOs
 * - DAO: provides access to specific data sources
 * - Client: requests assembled transfer objects
 * 
 * Benefits:
 * - Reduces network overhead in distributed systems
 * - Provides flexible data assembly strategies
 * - Centralizes complex data aggregation logic
 * - Supports different levels of detail for different clients
 * 
 * Use Cases:
 * - Distributed systems with multiple data sources
 * - REST APIs returning aggregated data
 * - Reporting systems combining multiple entities
 * - Mobile applications requiring optimized data transfer
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Transfer Object Assembler Pattern ===");
        System.out.println("Building complex transfer objects from multiple sources");
        System.out.println();
        
        System.out.println("=== Traditional Assembler ===");
        
        CustomerAssembler assembler = new CustomerAssembler();
        
        // Basic customer data only
        CustomerTO basicCustomer = assembler.assembleCustomerBasic("1");
        System.out.println("Basic customer: " + basicCustomer);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Customer with orders
        CustomerTO customerWithOrders = assembler.assembleCustomerWithOrders("1");
        System.out.println("Customer with orders: " + customerWithOrders);
        System.out.println("Orders: " + customerWithOrders.getOrders());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Complete customer data
        CustomerTO completeCustomer = assembler.assembleCustomer("1");
        System.out.println("Complete customer: " + completeCustomer);
        System.out.println("Orders: " + completeCustomer.getOrders());
        System.out.println("Account: " + completeCustomer.getAccount());
        
        System.out.println("\n=== Functional Assembler ===");
        
        FunctionalAssembler funcAssembler = new FunctionalAssembler();
        
        // Compose assemblers functionally
        Function<String, CustomerTO> basicFunc = funcAssembler.basicAssembler();
        CustomerTO funcBasic = basicFunc.apply("2");
        System.out.println("Functional basic: " + funcBasic);
        
        System.out.println("\n" + "-".repeat(30) + "\n");
        
        Function<String, CustomerTO> withOrdersFunc = funcAssembler.withOrders();
        CustomerTO funcWithOrders = withOrdersFunc.apply("2");
        System.out.println("Functional with orders: " + funcWithOrders);
        System.out.println("Orders: " + funcWithOrders.getOrders());
        
        System.out.println("\n" + "-".repeat(30) + "\n");
        
        Function<String, CustomerTO> fullFunc = funcAssembler.fullAssembler();
        CustomerTO funcComplete = fullFunc.apply("2");
        System.out.println("Functional complete: " + funcComplete);
        System.out.println("Orders: " + funcComplete.getOrders());
        System.out.println("Account: " + funcComplete.getAccount());
        
        System.out.println("\n=== Builder Pattern Integration ===");
        
        Function<String, CustomerTO> customAssembler = new FunctionalAssembler.AssemblerBuilder()
                .withOrders()
                .withAccount()
                .build();
        
        CustomerTO customResult = customAssembler.apply("1");
        System.out.println("Custom assembled: " + customResult);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Multiple assembly strategies for different needs");
        System.out.println("✅ Reduced network calls by combining related data");
        System.out.println("✅ Centralized data aggregation logic");
        System.out.println("✅ Flexible composition with functional approach");
        System.out.println("✅ Optimized data transfer for different clients");
        
        System.out.println("\n=== Performance Comparison ===");
        System.out.println("Without Assembler: 3 separate calls (Customer + Orders + Account)");
        System.out.println("With Assembler: 1 call returning complete object");
        System.out.println("Network overhead reduced by ~66%");
    }
}