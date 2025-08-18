package com.balazsholczer.adapter;

import java.util.List;

/**
 * Legacy StarTeam API Version 9
 * Original API that was used in the HP useit application
 */
public class StarTeamV9API {
    
    // Legacy method signatures from Version 9
    public void checkoutFile(String fileName) {
        System.out.println("StarTeam V9: Checking out file: " + fileName);
    }
    
    public void checkinFile(String fileName, String comment) {
        System.out.println("StarTeam V9: Checking in file: " + fileName + " with comment: " + comment);
    }
    
    public List<String> getFileHistory(String fileName) {
        System.out.println("StarTeam V9: Getting history for: " + fileName);
        return List.of("v1.0", "v1.1", "v1.2");
    }
    
    public boolean lockFile(String fileName) {
        System.out.println("StarTeam V9: Locking file: " + fileName);
        return true;
    }
    
    public void unlockFile(String fileName) {
        System.out.println("StarTeam V9: Unlocking file: " + fileName);
    }
    
    public String getFileStatus(String fileName) {
        System.out.println("StarTeam V9: Getting status for: " + fileName);
        return "CURRENT";
    }
}