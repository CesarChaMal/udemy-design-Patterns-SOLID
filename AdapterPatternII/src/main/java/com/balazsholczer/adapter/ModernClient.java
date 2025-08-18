package com.balazsholczer.adapter;

import java.util.List;
import java.util.Map;

/**
 * Modern client that can take advantage of both V9 compatibility and V14 features
 * Demonstrates gradual migration approach
 */
public class ModernClient {
    
    private final StarTeamAdapter adapter;
    
    public ModernClient(StarTeamAdapter adapter) {
        this.adapter = adapter;
    }
    
    public void performLegacyOperations(String fileName) {
        System.out.println("ModernClient: Using legacy V9 interface through adapter");
        
        // Use V9-compatible methods through adapter
        adapter.checkoutFile(fileName);
        adapter.checkinFile(fileName, "Updated via modern client using legacy interface");
        
        List<String> history = adapter.getFileHistory(fileName);
        System.out.println("ModernClient: Simple history: " + history);
    }
    
    public void performEnhancedOperations(String fileName) {
        System.out.println("ModernClient: Using enhanced V14 features through adapter");
        
        // Use enhanced V14 features when available
        Map<String, Object> detailedStatus = adapter.getDetailedStatus(fileName);
        System.out.println("ModernClient: Detailed status: " + detailedStatus);
        
        // Still use legacy interface for basic operations
        adapter.lockFile(fileName);
        adapter.unlockFile(fileName);
    }
    
    public void demonstrateGradualMigration(List<String> fileNames) {
        System.out.println("ModernClient: Demonstrating gradual migration approach");
        
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            
            if (i % 2 == 0) {
                System.out.println("Processing " + fileName + " with legacy approach:");
                performLegacyOperations(fileName);
            } else {
                System.out.println("Processing " + fileName + " with enhanced approach:");
                performEnhancedOperations(fileName);
            }
            System.out.println("---");
        }
        
        System.out.println("ModernClient: Gradual migration demonstration completed");
    }
}