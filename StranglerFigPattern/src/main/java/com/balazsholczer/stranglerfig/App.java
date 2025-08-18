package com.balazsholczer.stranglerfig;

/**
 * Strangler Fig Pattern: legacy system migration
 * 
 * Key Concepts:
 * - Gradually replaces legacy system functionality
 * - Routes requests between old and new implementations
 * - Enables incremental migration with rollback capability
 * - Minimizes risk during system modernization
 * 
 * Benefits:
 * - Risk-free incremental migration
 * - Ability to rollback if issues occur
 * - Continuous system operation during migration
 * - Gradual feature-by-feature replacement
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Strangler Fig Pattern ===");
        System.out.println("Legacy system migration");
        System.out.println();
        
        StranglerFacade facade = new StranglerFacade();
        
        // Phase 1: All requests go to legacy system
        System.out.println("--- Phase 1: Legacy System Only ---");
        System.out.println("User data: " + facade.getUserData("U123"));
        System.out.println("Order data: " + facade.getOrderData("O456"));
        
        // Phase 2: Migrate user service
        System.out.println("\n--- Phase 2: Migrate User Service ---");
        facade.migrateUserService();
        
        System.out.println("User data: " + facade.getUserData("U123"));
        System.out.println("Order data: " + facade.getOrderData("O456"));
        
        // Phase 3: Demonstrate rollback capability
        System.out.println("\n--- Phase 3: Rollback Demonstration ---");
        facade.rollbackUserService();
        
        System.out.println("User data: " + facade.getUserData("U123"));
        System.out.println("Order data: " + facade.getOrderData("O456"));
        
        // Phase 4: Final migration
        System.out.println("\n--- Phase 4: Final Migration ---");
        facade.migrateUserService();
        
        System.out.println("User data: " + facade.getUserData("U123"));
        System.out.println("Order data: " + facade.getOrderData("O456"));
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Risk-free incremental migration");
        System.out.println("✅ Rollback capability for safety");
        System.out.println("✅ Continuous operation during migration");
        System.out.println("✅ Gradual feature replacement");
    }
}