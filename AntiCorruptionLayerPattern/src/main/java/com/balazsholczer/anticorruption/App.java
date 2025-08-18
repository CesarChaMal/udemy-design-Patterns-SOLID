package com.balazsholczer.anticorruption;

/**
 * Anti-Corruption Layer Pattern: clean architecture boundaries
 * 
 * Key Concepts:
 * - Isolates domain model from external system influences
 * - Translates between different models and protocols
 * - Prevents legacy system design from corrupting new system
 * - Maintains clean boundaries between bounded contexts
 * 
 * Benefits:
 * - Protects domain model integrity
 * - Enables integration with poorly designed systems
 * - Maintains clean architecture principles
 * - Facilitates gradual system modernization
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Anti-Corruption Layer Pattern ===");
        System.out.println("Clean architecture boundaries");
        System.out.println();
        
        // Legacy system with poor design
        LegacyCustomerService legacyService = new LegacyCustomerService();
        
        // Anti-corruption layer protects our clean domain
        CustomerAntiCorruptionLayer acl = new CustomerAntiCorruptionLayer(legacyService);
        
        // Modern application works with clean model
        System.out.println("--- Getting Customer (Clean Interface) ---");
        ModernCustomer customer = acl.getCustomer("CUST001");
        System.out.println("Modern customer: " + customer);
        
        System.out.println("\n--- Updating Customer Status ---");
        acl.updateCustomerStatus("CUST001", ModernCustomer.CustomerStatus.INACTIVE);
        
        // Verify the update
        ModernCustomer updatedCustomer = acl.getCustomer("CUST001");
        System.out.println("Updated customer: " + updatedCustomer);
        
        System.out.println("\n--- Benefits Demonstration ---");
        System.out.println("✓ Modern application uses clean ModernCustomer model");
        System.out.println("✓ Legacy system continues using LegacyCustomer model");
        System.out.println("✓ Anti-corruption layer handles all translation");
        System.out.println("✓ Domain model protected from legacy system corruption");
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Protected domain model integrity");
        System.out.println("✅ Clean integration with legacy systems");
        System.out.println("✅ Maintained clean architecture principles");
        System.out.println("✅ Enabled gradual system modernization");
    }
}