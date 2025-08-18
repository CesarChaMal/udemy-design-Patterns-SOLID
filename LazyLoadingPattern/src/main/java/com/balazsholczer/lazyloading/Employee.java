package com.balazsholczer.lazyloading;

import java.util.List;

public class Employee {
    
    private String id;
    private String name;
    private LazyLoader<List<String>> projects;
    private LazyLoader<String> department;
    
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        
        // Lazy load projects
        this.projects = new LazyLoader<>(() -> {
            System.out.println("Employee: Loading projects for " + name);
            // Simulate expensive database call
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return List.of("Project A", "Project B", "Project C");
        });
        
        // Lazy load department
        this.department = new LazyLoader<>(() -> {
            System.out.println("Employee: Loading department for " + name);
            // Simulate expensive service call
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Engineering";
        });
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    
    public List<String> getProjects() {
        return projects.get();
    }
    
    public String getDepartment() {
        return department.get();
    }
    
    public boolean areProjectsLoaded() {
        return projects.isLoaded();
    }
    
    public boolean isDepartmentLoaded() {
        return department.isLoaded();
    }
    
    @Override
    public String toString() {
        return "Employee{id='" + id + "', name='" + name + "'}";
    }
}