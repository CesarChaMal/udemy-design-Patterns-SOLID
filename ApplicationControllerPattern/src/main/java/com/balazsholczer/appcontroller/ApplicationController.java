package com.balazsholczer.appcontroller;

import java.util.HashMap;
import java.util.Map;

public class ApplicationController {
    
    private final Map<String, String> navigationRules = new HashMap<>();
    private final Map<String, Command> commands = new HashMap<>();
    
    public ApplicationController() {
        initializeNavigationRules();
        initializeCommands();
    }
    
    private void initializeNavigationRules() {
        // Define navigation flow
        navigationRules.put("login_success", "dashboard");
        navigationRules.put("login_failure", "login");
        navigationRules.put("logout", "login");
        navigationRules.put("profile_update_success", "profile");
        navigationRules.put("profile_update_failure", "profile_edit");
        navigationRules.put("order_success", "order_confirmation");
        navigationRules.put("order_failure", "cart");
        
        System.out.println("ApplicationController: Navigation rules initialized");
    }
    
    private void initializeCommands() {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("order", new OrderCommand());
        
        System.out.println("ApplicationController: Commands initialized");
    }
    
    public String processRequest(String action, Map<String, Object> context) {
        System.out.println("ApplicationController: Processing action - " + action);
        
        // Execute command
        Command command = commands.get(action);
        if (command != null) {
            String result = command.execute(context);
            System.out.println("ApplicationController: Command result - " + result);
            
            // Determine next view based on result
            String nextView = getNextView(result);
            System.out.println("ApplicationController: Next view - " + nextView);
            
            return nextView;
        }
        
        return "error";
    }
    
    public String getNextView(String result) {
        return navigationRules.getOrDefault(result, "error");
    }
    
    public void addNavigationRule(String result, String view) {
        navigationRules.put(result, view);
        System.out.println("ApplicationController: Added navigation rule - " + result + " -> " + view);
    }
    
    public void addCommand(String action, Command command) {
        commands.put(action, command);
        System.out.println("ApplicationController: Added command - " + action);
    }
}