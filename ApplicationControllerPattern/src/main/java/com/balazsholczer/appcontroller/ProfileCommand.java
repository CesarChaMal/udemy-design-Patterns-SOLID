package com.balazsholczer.appcontroller;

import java.util.Map;

public class ProfileCommand implements Command {
    
    @Override
    public String execute(Map<String, Object> context) {
        System.out.println("ProfileCommand: Updating user profile");
        
        String email = (String) context.get("email");
        String phone = (String) context.get("phone");
        
        // Simulate profile update
        if (email != null && email.contains("@")) {
            context.put("profile_updated", true);
            context.put("message", "Profile updated successfully");
            return "profile_update_success";
        } else {
            context.put("error", "Invalid email format");
            return "profile_update_failure";
        }
    }
}