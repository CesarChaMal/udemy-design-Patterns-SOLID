package com.balazsholczer.pagecontroller;

/**
 * Page Controller Pattern: controller per page/view
 * 
 * Key Concepts:
 * - Each page has its own dedicated controller
 * - Controller handles all requests for specific page
 * - Encapsulates page-specific logic and behavior
 * - Provides clear separation between different pages
 * 
 * Benefits:
 * - Clear separation of page responsibilities
 * - Easy to understand and maintain
 * - Simple request routing
 * - Page-specific customization
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Page Controller Pattern ===");
        System.out.println("Controller per page/view");
        System.out.println();
        
        // Home page requests
        PageController homeController = new HomePageController();
        
        HttpRequest homeRequest = new HttpRequest("GET", "/home");
        homeRequest.addParameter("user", "John");
        
        HttpResponse homeResponse = homeController.handleRequest(homeRequest);
        System.out.println("Home response: " + homeResponse);
        System.out.println("Content: " + homeResponse.getContent().substring(0, 50) + "...");
        
        System.out.println();
        
        // Login page requests
        PageController loginController = new LoginPageController();
        
        // GET login page
        HttpRequest loginGetRequest = new HttpRequest("GET", "/login");
        HttpResponse loginGetResponse = loginController.handleRequest(loginGetRequest);
        System.out.println("Login GET response: " + loginGetResponse);
        
        // POST login (successful)
        HttpRequest loginPostRequest = new HttpRequest("POST", "/login");
        loginPostRequest.addParameter("username", "admin");
        loginPostRequest.addParameter("password", "password");
        
        HttpResponse loginPostResponse = loginController.handleRequest(loginPostRequest);
        System.out.println("Login POST response: " + loginPostResponse);
        System.out.println("Content: " + loginPostResponse.getContent().substring(0, 50) + "...");
        
        // POST login (failed)
        HttpRequest loginFailRequest = new HttpRequest("POST", "/login");
        loginFailRequest.addParameter("username", "user");
        loginFailRequest.addParameter("password", "wrong");
        
        HttpResponse loginFailResponse = loginController.handleRequest(loginFailRequest);
        System.out.println("Login FAIL response: " + loginFailResponse);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Clear separation of page responsibilities");
        System.out.println("✅ Page-specific logic encapsulation");
        System.out.println("✅ Simple and maintainable structure");
        System.out.println("✅ Easy request handling per page");
    }
}