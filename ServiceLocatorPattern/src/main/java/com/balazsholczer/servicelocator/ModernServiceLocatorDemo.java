package com.balazsholczer.servicelocator;

public class ModernServiceLocatorDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Service Locator Pattern ===");
        Service traditional1 = ServiceLocator.getService("databaseService");
        traditional1.execute();
        Service traditional2 = ServiceLocator.getService("messagingService");
        traditional2.execute();
        
        System.out.println("\n=== Functional Service Locator Pattern ===");
        Service functional1 = FunctionalServiceLocator.getService("databaseService");
        functional1.execute();
        Service functional2 = FunctionalServiceLocator.getService("messagingService");
        functional2.execute();
        
        System.out.println("\n=== Enum Service Locator Pattern ===");
        Service enum1 = EnumServiceLocator.getService("databaseService");
        enum1.execute();
        Service enum2 = EnumServiceLocator.getService("messagingService");
        enum2.execute();
        
        System.out.println("\n=== Dependency Injection Container ===");
        DIServiceContainer container = DIServiceContainer.create();
        DatabaseService db = container.getService(DatabaseService.class);
        db.execute();
        MessagingService msg = container.getService(MessagingService.class);
        msg.execute();
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - Error Handling:");
        Service unknown = FunctionalServiceLocator.getService("unknownService");
        unknown.execute();
        
        System.out.println("Enum - Singleton Behavior:");
        Service enum3 = EnumServiceLocator.getService("databaseService");
        Service enum4 = EnumServiceLocator.getService("databaseService");
        System.out.println("Same instance: " + (enum3 == enum4));
        
        System.out.println("DI Container - Type Safety:");
        try {
            container.getService(String.class);
        } catch (IllegalArgumentException e) {
            System.out.println("Type safety: " + e.getMessage());
        }
    }
}