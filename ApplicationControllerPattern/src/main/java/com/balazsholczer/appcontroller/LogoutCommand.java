package com.balazsholczer.appcontroller;

import java.util.Map;

public class LogoutCommand implements Command {
    
    @Override
    public String execute(Map<String, Object> context) {
        System.out.println("LogoutCommand: Logging out user");
        
        context.remove("user");
        context.put("authenticated", false);
        context.put("message", "Successfully logged out");
        
        return "logout";
    }
}