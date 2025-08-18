package com.balazsholczer.adapter;

import java.util.List;

public class ModernAdapterDemo {
    
    public static void main(String[] args) {
        System.out.println("=== StarTeam API Migration: V9 to V14 ===");
        System.out.println("Real-world scenario: HP useit application migration");
        System.out.println();
        
        // Initialize V14 API (new system)
        StarTeamV14API v14API = new StarTeamV14API();
        
        System.out.println("=== Traditional Adapter Pattern ===");
        
        // Create adapter to bridge V9 client code to V14 API
        StarTeamAdapter adapter = new StarTeamAdapter(v14API);
        
        // Legacy client can work unchanged with adapter
        LegacyClient legacyClient = new LegacyClient(adapter);
        legacyClient.performFileOperations("legacy-file.java");
        
        System.out.println("\n=== Gradual Migration Approach ===");
        
        // Modern client can use both legacy and enhanced features
        ModernClient modernClient = new ModernClient(adapter);
        List<String> files = List.of("file1.java", "file2.java", "file3.java", "file4.java");
        modernClient.demonstrateGradualMigration(files);
        
        System.out.println("\n=== Functional Adapter Pattern ===");
        
        FunctionalAdapter functionalAdapter = new FunctionalAdapter(v14API);
        
        // Use functional approach
        functionalAdapter.checkoutFile.execute("functional-file.java");
        
        var checkinRequest = new FunctionalAdapter.CheckinRequest("functional-file.java", "Updated functionally");
        functionalAdapter.checkinFile.execute(checkinRequest);
        
        List<String> history = functionalAdapter.getFileHistory.execute("functional-file.java");
        System.out.println("Functional history: " + history);
        
        String status = functionalAdapter.getFileStatus.execute("functional-file.java");
        System.out.println("Functional status: " + status);
        
        System.out.println("\n=== Migration Benefits Demonstrated ===");
        
        System.out.println("✅ Legacy code works unchanged");
        System.out.println("✅ Gradual migration over multiple releases");
        System.out.println("✅ Enhanced features available when needed");
        System.out.println("✅ Reduced risk through incremental approach");
        System.out.println("✅ Both APIs can coexist during transition");
        
        System.out.println("\n=== Batch Processing Comparison ===");
        
        System.out.println("Legacy batch processing:");
        legacyClient.batchFileOperations(List.of("batch1.java", "batch2.java"));
        
        System.out.println("\n=== Real-World Migration Timeline ===");
        System.out.println("Release 1: Introduce adapter, keep V9 interface");
        System.out.println("Release 2: Migrate 25% of operations to use enhanced features");
        System.out.println("Release 3: Migrate 50% of operations to use enhanced features");
        System.out.println("Release 4: Migrate 75% of operations to use enhanced features");
        System.out.println("Release 5: Complete migration, remove V9 compatibility layer");
        
        System.out.println("\n=== Pattern Comparison ===");
        System.out.println("Traditional: Object-based adapter with method delegation");
        System.out.println("Functional: Lambda-based adapter with composable operations");
        System.out.println("Both: Enable gradual migration and backward compatibility");
    }
}