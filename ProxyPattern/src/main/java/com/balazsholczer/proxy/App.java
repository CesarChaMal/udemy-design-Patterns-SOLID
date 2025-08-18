package com.balazsholczer.proxy;

/**
 * Proxy Pattern: provides a placeholder or surrogate for another object to control access to it
 * 
 * Types of Proxies:
 * - Virtual Proxy: lazy initialization (our image example)
 * - Protection Proxy: access control
 * - Remote Proxy: represents object in different address space
 * - Caching Proxy: caches results of expensive operations
 * 
 * Benefits:
 * - Lazy loading of expensive objects
 * - Access control and security
 * - Caching and performance optimization
 * - Additional functionality without changing original object
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Proxy Pattern ===");
        Image image = new ImageProxy("large_photo.jpg");
        System.out.println("Proxy created - no loading yet");
        
        image.display(); // Now it loads
        image.display(); // Uses already loaded image
        
        System.out.println("\n=== Run ModernProxyDemo for all approaches ===");
    }
}