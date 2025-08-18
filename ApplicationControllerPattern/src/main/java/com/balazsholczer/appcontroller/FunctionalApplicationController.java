package com.balazsholczer.appcontroller;

import java.util.Map;
import java.util.function.Function;

public class FunctionalApplicationController {
    
    private final Map<String, Function<Map<String, Object>, String>> actions;
    private final Map<String, String> navigationRules;
    
    public FunctionalApplicationController() {
        this.actions = Map.of(
            "login", this::loginAction,
            "logout", this::logoutAction,
            "profile", this::profileAction,
            "order", this::orderAction
        );
        
        this.navigationRules = Map.of(
            "login_success", "dashboard",
            "login_failure", "login",
            "logout", "login",
            "profile_update_success", "profile",
            "order_success", "order_confirmation"
        );
        
        System.out.println("FunctionalApplicationController: Initialized with functional actions");
    }
    
    public String processRequest(String action, Map<String, Object> context) {
        System.out.println("FunctionalApplicationController: Processing - " + action);
        
        Function<Map<String, Object>, String> actionFunction = actions.get(action);
        if (actionFunction != null) {
            String result = actionFunction.apply(context);
            return navigationRules.getOrDefault(result, "error");
        }
        
        return "error";
    }
    
    private String loginAction(Map<String, Object> context) {
        System.out.println("Functional: Login action");
        String username = (String) context.get("username");
        return "admin".equals(username) ? "login_success" : "login_failure";
    }
    
    private String logoutAction(Map<String, Object> context) {
        System.out.println("Functional: Logout action");
        context.clear();
        return "logout";
    }
    
    private String profileAction(Map<String, Object> context) {
        System.out.println("Functional: Profile action");
        String email = (String) context.get("email");
        return (email != null && email.contains("@")) ? "profile_update_success" : "profile_update_failure";
    }
    
    private String orderAction(Map<String, Object> context) {
        System.out.println("Functional: Order action");
        Integer quantity = (Integer) context.get("quantity");
        return (quantity != null && quantity > 0) ? "order_success" : "order_failure";
    }
}