package com.balazsholczer.context;

import java.util.HashSet;
import java.util.Set;

public class SecurityContext {
    
    private String principal;
    private Set<String> roles;
    private Set<String> permissions;
    private boolean authenticated;
    
    public SecurityContext() {
        this.roles = new HashSet<>();
        this.permissions = new HashSet<>();
        this.authenticated = false;
        System.out.println("SecurityContext: Created security context");
    }
    
    public String getPrincipal() { return principal; }
    public void setPrincipal(String principal) { 
        this.principal = principal;
        System.out.println("SecurityContext: Set principal - " + principal);
    }
    
    public boolean isAuthenticated() { return authenticated; }
    public void setAuthenticated(boolean authenticated) { 
        this.authenticated = authenticated;
        System.out.println("SecurityContext: Set authenticated - " + authenticated);
    }
    
    public void addRole(String role) {
        roles.add(role);
        System.out.println("SecurityContext: Added role - " + role);
    }
    
    public boolean hasRole(String role) {
        return roles.contains(role);
    }
    
    public Set<String> getRoles() {
        return new HashSet<>(roles);
    }
    
    public void addPermission(String permission) {
        permissions.add(permission);
        System.out.println("SecurityContext: Added permission - " + permission);
    }
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    public Set<String> getPermissions() {
        return new HashSet<>(permissions);
    }
    
    public void clearContext() {
        principal = null;
        roles.clear();
        permissions.clear();
        authenticated = false;
        System.out.println("SecurityContext: Cleared security context");
    }
    
    @Override
    public String toString() {
        return "SecurityContext{principal='" + principal + "', authenticated=" + authenticated + 
               ", roles=" + roles + ", permissions=" + permissions + "}";
    }
}