package com.balazsholczer.di;

/**
 * Dependency Injection Pattern: provides dependencies to objects instead of creating them
 * 
 * Key Concepts:
 * - Inversion of Control (IoC) - dependencies are provided, not created
 * - Loose coupling between objects and their dependencies
 * - Container manages object lifecycle and dependency resolution
 * - Supports constructor, setter, and interface injection
 * 
 * Structure:
 * - DIContainer: manages object creation and dependency injection
 * - Service: business logic that depends on other components
 * - Repository: data access layer injected into services
 * - Client: uses container to get fully configured objects
 * 
 * Benefits:
 * - Loose coupling and high testability
 * - Easy to swap implementations
 * - Centralized dependency management
 * - Supports different object lifecycles (singleton, prototype)
 * 
 * Use Cases:
 * - Enterprise applications with complex dependencies
 * - Unit testing with mock dependencies
 * - Plugin architectures with swappable components
 * - Configuration-driven dependency resolution
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Dependency Injection Pattern ===");
        System.out.println("Inversion of Control container for dependency management");
        System.out.println();
        
        System.out.println("=== Manual Dependency Injection ===");
        
        // Manual dependency injection (without container)
        UserRepository repository = new DatabaseUserRepository();
        UserService userService = new UserService(repository);
        
        userService.createUser("1", "John Doe", "john@example.com");
        User user = userService.getUser("1");
        System.out.println("Retrieved user: " + user);
        
        System.out.println("\n=== DI Container ===");
        
        // Using DI container
        DIContainer container = new DIContainer();
        
        // Register dependencies
        container.registerSingleton(UserRepository.class, new DatabaseUserRepository());
        container.registerFactory(UserService.class, () -> 
            new UserService(container.getInstance(UserRepository.class)));
        
        // Get fully configured service
        UserService containerService = container.getInstance(UserService.class);
        containerService.createUser("2", "Jane Smith", "jane@example.com");
        
        // Auto-wiring example
        UserService autoWiredService = container.createInstance(UserService.class);
        autoWiredService.createUser("3", "Bob Johnson", "bob@example.com");
        
        System.out.println("\n=== Functional DI Container ===");
        
        FunctionalDIContainer funcContainer = new FunctionalDIContainer();
        
        // Register with lambda expressions
        funcContainer.registerSingleton("userRepo", new DatabaseUserRepository());
        funcContainer.register("userService", () -> 
            new UserService(funcContainer.get("userRepo")));
        
        UserService funcService = funcContainer.get("userService");
        funcService.createUser("4", "Alice Brown", "alice@example.com");
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Loose coupling - service doesn't create repository");
        System.out.println("✅ Easy testing - can inject mock dependencies");
        System.out.println("✅ Flexible configuration - swap implementations easily");
        System.out.println("✅ Centralized management - container handles lifecycle");
        System.out.println("✅ Inversion of Control - dependencies are provided");
        
        System.out.println("\n=== Dependency Lifecycle ===");
        
        // Demonstrate singleton behavior
        UserRepository repo1 = container.getInstance(UserRepository.class);
        UserRepository repo2 = container.getInstance(UserRepository.class);
        System.out.println("Same singleton instance: " + (repo1 == repo2));
        
        // Demonstrate factory behavior (new instance each time)
        UserService service1 = container.getInstance(UserService.class);
        UserService service2 = container.getInstance(UserService.class);
        System.out.println("Different service instances: " + (service1 != service2));
    }
}