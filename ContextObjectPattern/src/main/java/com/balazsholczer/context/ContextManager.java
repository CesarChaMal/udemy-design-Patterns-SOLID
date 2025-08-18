package com.balazsholczer.context;

public class ContextManager {
    
    private static final ThreadLocal<ApplicationContext> contextHolder = new ThreadLocal<>();
    
    public static void setContext(ApplicationContext context) {
        contextHolder.set(context);
        System.out.println("ContextManager: Set context for thread " + Thread.currentThread().getName());
    }
    
    public static ApplicationContext getContext() {
        ApplicationContext context = contextHolder.get();
        if (context == null) {
            context = new ApplicationContext();
            contextHolder.set(context);
            System.out.println("ContextManager: Created new context for thread " + Thread.currentThread().getName());
        }
        return context;
    }
    
    public static void clearContext() {
        ApplicationContext context = contextHolder.get();
        if (context != null) {
            context.cleanup();
            contextHolder.remove();
            System.out.println("ContextManager: Cleared context for thread " + Thread.currentThread().getName());
        }
    }
    
    // Convenience methods
    public static RequestContext getRequestContext() {
        return getContext().getRequestContext();
    }
    
    public static SecurityContext getSecurityContext() {
        return getContext().getSecurityContext();
    }
    
    public static boolean isAuthenticated() {
        return getContext().isUserAuthenticated();
    }
    
    public static String getCurrentUser() {
        return getContext().getCurrentUser();
    }
    
    public static boolean hasRole(String role) {
        return getContext().hasRole(role);
    }
    
    public static boolean hasPermission(String permission) {
        return getContext().hasPermission(permission);
    }
}