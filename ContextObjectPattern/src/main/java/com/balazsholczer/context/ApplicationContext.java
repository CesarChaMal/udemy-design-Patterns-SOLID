package com.balazsholczer.context;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    
    private final RequestContext requestContext;
    private final SecurityContext securityContext;
    private final Map<String, Object> applicationData;
    
    public ApplicationContext() {
        this.requestContext = new RequestContext();
        this.securityContext = new SecurityContext();
        this.applicationData = new HashMap<>();
        System.out.println("ApplicationContext: Created application context");
    }
    
    public RequestContext getRequestContext() {
        return requestContext;
    }
    
    public SecurityContext getSecurityContext() {
        return securityContext;
    }
    
    public void setApplicationData(String key, Object value) {
        applicationData.put(key, value);
        System.out.println("ApplicationContext: Set application data " + key + " = " + value);
    }
    
    public Object getApplicationData(String key) {
        return applicationData.get(key);
    }
    
    public boolean hasApplicationData(String key) {
        return applicationData.containsKey(key);
    }
    
    // Convenience methods
    public boolean isUserAuthenticated() {
        return securityContext.isAuthenticated();
    }
    
    public boolean hasRole(String role) {
        return securityContext.hasRole(role);
    }
    
    public boolean hasPermission(String permission) {
        return securityContext.hasPermission(permission);
    }
    
    public String getCurrentUser() {
        return securityContext.getPrincipal();
    }
    
    public String getRequestId() {
        return requestContext.getRequestId();
    }
    
    public void cleanup() {
        securityContext.clearContext();
        applicationData.clear();
        System.out.println("ApplicationContext: Cleaned up context");
    }
    
    @Override
    public String toString() {
        return "ApplicationContext{requestId='" + requestContext.getRequestId() + 
               "', user='" + securityContext.getPrincipal() + 
               "', authenticated=" + securityContext.isAuthenticated() + "}";
    }
}