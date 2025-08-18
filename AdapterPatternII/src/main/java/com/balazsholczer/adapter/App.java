package com.balazsholczer.adapter;

import java.util.List;

/**
 * Adapter Pattern: Real-World StarTeam API Migration Case Study
 * 
 * PROBLEM DESCRIPTION:
 * 10 years ago while working for HP's useit application, we faced a critical challenge:
 * - StarTeam API needed to be upgraded from Version 9 to Version 14
 * - Version 14 had completely different method signatures (incompatible changes)
 * - Large codebase with hundreds of V9 API calls throughout the application
 * - Business requirement: Zero downtime migration over several releases
 * - Risk mitigation: Gradual migration to minimize production issues
 * 
 * SOLUTION:
 * Implemented Adapter Pattern to bridge V9 client code with V14 API backend
 * This enabled gradual migration over multiple releases while maintaining functionality
 * 
 * Key Concepts:
 * - Makes incompatible interfaces work together
 * - Allows legacy code to work with new APIs without modification
 * - Enables gradual migration strategies
 * - Wraps new interface to match old interface expectations
 * 
 * Structure:
 * - Target: interface that client expects (V9 API)
 * - Adaptee: new interface that needs adapting (V14 API)
 * - Adapter: bridges Target and Adaptee interfaces
 * - Client: uses Target interface (legacy code)
 * 
 * Benefits:
 * - Enables reuse of existing code with new systems
 * - Separates interface conversion from business logic
 * - Supports gradual migration strategies
 * - Reduces risk in large system upgrades
 * 
 * Use Cases:
 * - API version migrations (like StarTeam V9→V14)
 * - Third-party library integration
 * - Legacy system modernization
 * - Database driver updates
 * - Payment gateway switches
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== StarTeam API Migration: Real-World Case Study ===");
        System.out.println("HP useit Application - V9 to V14 Migration");
        System.out.println();
        
        System.out.println("PROBLEM:");
        System.out.println("- StarTeam V9 API: checkoutFile(String fileName)");
        System.out.println("- StarTeam V14 API: checkoutFileWithOptions(String filePath, Map<String, Object> options)");
        System.out.println("- Incompatible method signatures across entire codebase");
        System.out.println("- Need gradual migration over several releases");
        System.out.println();
        
        // Demonstrate the problem: V9 and V14 APIs are incompatible
        System.out.println("=== Before Adapter: Incompatible APIs ===");
        
        StarTeamV9API v9API = new StarTeamV9API();
        StarTeamV14API v14API = new StarTeamV14API();
        
        // V9 API calls (legacy code)
        v9API.checkoutFile("legacy-file.java");
        v9API.checkinFile("legacy-file.java", "Legacy update");
        
        // V14 API calls (new system) - different signatures!
        v14API.checkoutFileWithOptions("new-file.java", java.util.Map.of("overwrite", true));
        v14API.checkinFileWithMetadata("new-file.java", "New update", java.util.Map.of("author", "system"));
        
        System.out.println("\n=== Solution: Adapter Pattern ===");
        
        // Create adapter to bridge V9 client code to V14 backend
        StarTeamAdapter adapter = new StarTeamAdapter(v14API);
        
        // Legacy client code works unchanged through adapter
        LegacyClient legacyClient = new LegacyClient(adapter);
        
        System.out.println("Legacy client using V9 interface → Adapter → V14 backend:");
        legacyClient.performFileOperations("migrated-file.java");
        
        System.out.println("\n=== Gradual Migration Strategy ===");
        
        // Demonstrate how migration happened over multiple releases
        List<String> files = List.of("file1.java", "file2.java", "file3.java");
        
        System.out.println("Release 1-2: All operations through V9 interface (safe)");
        for (String file : files) {
            adapter.checkoutFile(file);  // V9 signature → V14 implementation
            adapter.checkinFile(file, "Migrated via adapter");
        }
        
        System.out.println("\nRelease 3-4: Gradually introduce V14 features");
        ModernClient modernClient = new ModernClient(adapter);
        modernClient.performEnhancedOperations("enhanced-file.java");
        
        System.out.println("\n=== Migration Success Metrics ===");
        System.out.println("✅ Zero production downtime during migration");
        System.out.println("✅ Legacy code worked unchanged");
        System.out.println("✅ Gradual feature adoption over 5 releases");
        System.out.println("✅ Risk minimized through incremental approach");
        System.out.println("✅ Enhanced V14 features available when ready");
        
        System.out.println("\n=== Run ModernAdapterDemo for complete implementation ===");
    }
}