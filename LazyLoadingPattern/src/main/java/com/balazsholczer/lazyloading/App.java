package com.balazsholczer.lazyloading;

/**
 * Lazy Loading Pattern: defers object loading until needed
 * 
 * Key Concepts:
 * - Delays expensive operations until actually needed
 * - Improves initial object creation performance
 * - Reduces memory usage for unused properties
 * - Provides transparent access to lazily loaded data
 * 
 * Benefits:
 * - Improved application startup time
 * - Reduced memory footprint
 * - Better performance for large object graphs
 * - Transparent lazy initialization
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Lazy Loading Pattern ===");
        System.out.println("Deferring object loading until needed");
        System.out.println();
        
        // Create employee (fast - no expensive operations yet)
        System.out.println("Creating employee...");
        Employee employee = new Employee("EMP-001", "John Doe");
        System.out.println("Employee created: " + employee);
        System.out.println("Projects loaded: " + employee.areProjectsLoaded());
        System.out.println("Department loaded: " + employee.isDepartmentLoaded());
        
        System.out.println("\n--- First access to projects ---");
        // First access triggers loading
        var projects = employee.getProjects();
        System.out.println("Projects: " + projects);
        System.out.println("Projects loaded: " + employee.areProjectsLoaded());
        
        System.out.println("\n--- Second access to projects ---");
        // Second access uses cached value
        var projectsAgain = employee.getProjects();
        System.out.println("Projects: " + projectsAgain);
        
        System.out.println("\n--- First access to department ---");
        // Department is loaded separately
        String department = employee.getDepartment();
        System.out.println("Department: " + department);
        System.out.println("Department loaded: " + employee.isDepartmentLoaded());
        
        System.out.println("\n--- Second access to department ---");
        String departmentAgain = employee.getDepartment();
        System.out.println("Department: " + departmentAgain);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Deferred expensive operations");
        System.out.println("✅ Improved object creation performance");
        System.out.println("✅ Reduced memory usage");
        System.out.println("✅ Transparent lazy initialization");
    }
}