package com.balazsholczer.context;

/**
 * Context Object Pattern: encapsulates system state and context information
 * 
 * Key Concepts:
 * - Encapsulates system state and context information
 * - Provides unified access to distributed context data
 * - Manages context lifecycle and cleanup
 * - Supports thread-local context for multi-threaded applications
 * 
 * Structure:
 * - ContextObject: encapsulates context data and operations
 * - ContextManager: manages context lifecycle and access
 * - RequestContext: request-specific context information
 * - SecurityContext: security-related context data
 * 
 * Benefits:
 * - Centralized context management
 * - Thread-safe context access
 * - Simplified context passing between components
 * - Consistent context lifecycle management
 * 
 * Use Cases:
 * - Web applications with request/session context
 * - Security frameworks with authentication context
 * - Multi-threaded applications with thread-local context
 * - Distributed systems with correlation context
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Context Object Pattern ===");
        System.out.println("Encapsulating system state and context information");
        System.out.println();
        
        System.out.println("=== Traditional Context Objects ===");
        
        // Create application context
        ApplicationContext appContext = new ApplicationContext();
        
        // Set request context
        RequestContext requestContext = appContext.getRequestContext();
        requestContext.setUserId("user123");
        requestContext.setSessionId("session456");
        requestContext.setClientIP("192.168.1.100");
        requestContext.setAttribute("locale", "en_US");
        requestContext.setAttribute("theme", "dark");
        
        // Set security context
        SecurityContext securityContext = appContext.getSecurityContext();
        securityContext.setPrincipal("john.doe");
        securityContext.setAuthenticated(true);
        securityContext.addRole("USER");
        securityContext.addRole("ADMIN");
        securityContext.addPermission("READ");
        securityContext.addPermission("WRITE");
        
        // Set application data
        appContext.setApplicationData("currentPage", "dashboard");
        appContext.setApplicationData("lastActivity", System.currentTimeMillis());
        
        System.out.println("Application Context: " + appContext);
        System.out.println("Request Context: " + requestContext);
        System.out.println("Security Context: " + securityContext);
        
        System.out.println("\n=== Context Manager (Thread-Local) ===");
        
        // Use context manager for thread-local access
        ContextManager.setContext(appContext);
        
        // Access context from anywhere in the application
        System.out.println("Current user: " + ContextManager.getCurrentUser());
        System.out.println("Is authenticated: " + ContextManager.isAuthenticated());
        System.out.println("Has ADMIN role: " + ContextManager.hasRole("ADMIN"));
        System.out.println("Has WRITE permission: " + ContextManager.hasPermission("WRITE"));
        
        // Simulate business logic that uses context
        processBusinessLogic();
        
        System.out.println("\n=== Multi-threaded Context ===");
        
        // Demonstrate thread-local context
        Thread thread1 = new Thread(() -> {
            ApplicationContext ctx1 = new ApplicationContext();
            ctx1.getSecurityContext().setPrincipal("thread1_user");
            ctx1.getSecurityContext().setAuthenticated(true);
            ContextManager.setContext(ctx1);
            
            System.out.println("Thread 1 - Current user: " + ContextManager.getCurrentUser());
            
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            System.out.println("Thread 1 - Still current user: " + ContextManager.getCurrentUser());
            ContextManager.clearContext();
        });
        
        Thread thread2 = new Thread(() -> {
            ApplicationContext ctx2 = new ApplicationContext();
            ctx2.getSecurityContext().setPrincipal("thread2_user");
            ctx2.getSecurityContext().setAuthenticated(true);
            ContextManager.setContext(ctx2);
            
            System.out.println("Thread 2 - Current user: " + ContextManager.getCurrentUser());
            
            try { Thread.sleep(50); } catch (InterruptedException e) {}
            
            System.out.println("Thread 2 - Still current user: " + ContextManager.getCurrentUser());
            ContextManager.clearContext();
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== Functional Context ===");
        
        // Functional approach with fluent API
        FunctionalContext funcContext = FunctionalContext.create()
                .with("user", "functional_user")
                .with("authenticated", true)
                .apply(FunctionalContext.authenticate("admin"))
                .apply(FunctionalContext.addRole("ADMIN"))
                .apply(FunctionalContext.addRole("USER"))
                .with("requestId", "REQ-FUNC-123");
        
        System.out.println("Functional context: " + funcContext);
        
        // Transform context
        Boolean hasAdminRole = funcContext.transform(FunctionalContext.hasRole("ADMIN"));
        System.out.println("Has ADMIN role: " + hasAdminRole);
        
        // Compute values
        funcContext.compute("timestamp", current -> System.currentTimeMillis())
                  .computeIfAbsent("sessionId", () -> "SESS-" + System.currentTimeMillis());
        
        System.out.println("Enhanced functional context: " + funcContext);
        
        // Cleanup
        ContextManager.clearContext();
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Centralized context management");
        System.out.println("✅ Thread-safe context access");
        System.out.println("✅ Unified access to distributed context data");
        System.out.println("✅ Consistent context lifecycle management");
        System.out.println("✅ Functional context operations with fluent API");
    }
    
    private static void processBusinessLogic() {
        System.out.println("Business Logic: Processing for user " + ContextManager.getCurrentUser());
        
        if (ContextManager.hasPermission("WRITE")) {
            System.out.println("Business Logic: User has write permission - allowing operation");
        } else {
            System.out.println("Business Logic: User lacks write permission - denying operation");
        }
        
        RequestContext reqCtx = ContextManager.getRequestContext();
        String locale = (String) reqCtx.getAttribute("locale");
        System.out.println("Business Logic: Using locale " + locale + " for response");
    }
}