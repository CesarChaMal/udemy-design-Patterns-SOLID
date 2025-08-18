package com.balazsholczer.appcontroller;

import java.util.Map;

public class LoginCommand implements Command {
    
    @Override
    public String execute(Map<String, Object> context) {
        String username = (String) context.get("username");
        String password = (String) context.get("password");
        
        System.out.println("LoginCommand: Authenticating user - " + username);
        
        // Simulate authentication
        if ("admin".equals(username) && "password".equals(password)) {
            context.put("user", username);
            context.put("authenticated", true);
            return "login_success";
        } else {
            context.put("error", "Invalid credentials");
            return "login_failure";
        }
    }
}