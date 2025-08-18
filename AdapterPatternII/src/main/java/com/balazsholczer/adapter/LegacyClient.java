package com.balazsholczer.adapter;

import java.util.List;

/**
 * Legacy client code from HP useit application
 * This code was written to work with StarTeam V9 API
 */
public class LegacyClient {
    
    private final StarTeamV9API starTeamAPI;
    
    public LegacyClient(StarTeamV9API starTeamAPI) {
        this.starTeamAPI = starTeamAPI;
    }
    
    public void performFileOperations(String fileName) {
        System.out.println("LegacyClient: Starting file operations for: " + fileName);
        
        // Original V9 API calls - no changes needed in client code
        starTeamAPI.checkoutFile(fileName);
        
        String status = starTeamAPI.getFileStatus(fileName);
        System.out.println("LegacyClient: File status: " + status);
        
        boolean locked = starTeamAPI.lockFile(fileName);
        if (locked) {
            System.out.println("LegacyClient: File locked successfully");
            
            // Simulate some work
            starTeamAPI.checkinFile(fileName, "Updated via legacy client");
            
            starTeamAPI.unlockFile(fileName);
            System.out.println("LegacyClient: File unlocked");
        }
        
        List<String> history = starTeamAPI.getFileHistory(fileName);
        System.out.println("LegacyClient: File history: " + history);
        
        System.out.println("LegacyClient: File operations completed");
    }
    
    public void batchFileOperations(List<String> fileNames) {
        System.out.println("LegacyClient: Processing batch of " + fileNames.size() + " files");
        
        for (String fileName : fileNames) {
            performFileOperations(fileName);
            System.out.println("---");
        }
        
        System.out.println("LegacyClient: Batch processing completed");
    }
}