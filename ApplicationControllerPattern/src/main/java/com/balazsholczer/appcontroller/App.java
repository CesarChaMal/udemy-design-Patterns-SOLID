package com.balazsholczer.appcontroller;

import java.util.HashMap;
import java.util.Map;

/**
 * Application Controller Pattern: centralized application flow and navigation control
 * 
 * Key Concepts:
 * - Centralizes application flow control and navigation logic
 * - Maps actions to commands and results to views
 * - Separates navigation logic from presentation logic
 * - Provides consistent application flow management
 * 
 * Structure:
 * - ApplicationController: manages application flow and navigation
 * - Command: executes business logic for specific actions
 * - NavigationRules: maps results to next views/pages
 * - Context: carries data between commands and views
 * 
 * Benefits:
 * - Centralized navigation logic
 * - Consistent application flow
 * - Easy to modify navigation rules
 * - Separation of concerns between flow and presentation
 * 
 * Use Cases:
 * - Web applications with complex navigation flows
 * - Wizard-style interfaces with multiple steps
 * - Applications with conditional navigation
 * - Multi-page forms with validation flows
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Application Controller Pattern ===");
        System.out.println("Centralized application flow and navigation control");
        System.out.println();
        
        System.out.println("=== Traditional Application Controller ===");
        
        ApplicationController appController = new ApplicationController();
        
        // Simulate login flow
        Map<String, Object> loginContext = new HashMap<>();
        loginContext.put("username", "admin");
        loginContext.put("password", "password");
        
        String loginResult = appController.processRequest("login", loginContext);
        System.out.println("Login flow result: " + loginResult);
        System.out.println("Context after login: " + loginContext);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Simulate profile update flow
        Map<String, Object> profileContext = new HashMap<>();
        profileContext.put("email", "user@example.com");
        profileContext.put("phone", "123-456-7890");
        
        String profileResult = appController.processRequest("profile", profileContext);
        System.out.println("Profile update result: " + profileResult);
        System.out.println("Context after profile update: " + profileContext);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Simulate order flow
        Map<String, Object> orderContext = new HashMap<>();
        orderContext.put("product", "Laptop");
        orderContext.put("quantity", 2);
        
        String orderResult = appController.processRequest("order", orderContext);
        System.out.println("Order flow result: " + orderResult);
        System.out.println("Context after order: " + orderContext);
        
        System.out.println("\n=== Failed Login Flow ===");
        
        Map<String, Object> failedLoginContext = new HashMap<>();
        failedLoginContext.put("username", "user");
        failedLoginContext.put("password", "wrong");
        
        String failedLoginResult = appController.processRequest("login", failedLoginContext);
        System.out.println("Failed login result: " + failedLoginResult);
        System.out.println("Context after failed login: " + failedLoginContext);
        
        System.out.println("\n=== Functional Application Controller ===");
        
        FunctionalApplicationController funcController = new FunctionalApplicationController();
        
        // Test functional approach
        Map<String, Object> funcContext = new HashMap<>();
        funcContext.put("username", "admin");
        
        String funcResult = funcController.processRequest("login", funcContext);
        System.out.println("Functional login result: " + funcResult);
        
        // Test functional profile update
        Map<String, Object> funcProfileContext = new HashMap<>();
        funcProfileContext.put("email", "functional@example.com");
        
        String funcProfileResult = funcController.processRequest("profile", funcProfileContext);
        System.out.println("Functional profile result: " + funcProfileResult);
        
        System.out.println("\n=== Dynamic Navigation Rules ===");
        
        // Add custom navigation rule
        appController.addNavigationRule("custom_success", "custom_page");
        appController.addCommand("custom", context -> {
            System.out.println("Custom command executed");
            return "custom_success";
        });
        
        Map<String, Object> customContext = new HashMap<>();
        String customResult = appController.processRequest("custom", customContext);
        System.out.println("Custom flow result: " + customResult);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Centralized navigation logic");
        System.out.println("✅ Consistent application flow control");
        System.out.println("✅ Easy to modify navigation rules");
        System.out.println("✅ Separation of flow logic from presentation");
        System.out.println("✅ Context passing between commands and views");
        
        System.out.println("\n=== Navigation Flow Examples ===");
        System.out.println("Login Success: login -> dashboard");
        System.out.println("Login Failure: login -> login (with error)");
        System.out.println("Profile Update: profile -> profile (with success message)");
        System.out.println("Order Success: order -> order_confirmation");
        System.out.println("Logout: any_page -> login");
    }
}