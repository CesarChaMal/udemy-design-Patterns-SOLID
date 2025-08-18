package com.balazsholczer.frontcontroller;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    
    private final Map<String, Command> commands = new HashMap<>();
    
    public Dispatcher() {
        // Register commands
        commands.put("/", new HomeCommand());
        commands.put("/home", new HomeCommand());
        commands.put("/user", new UserCommand());
    }
    
    public void dispatch(Request request, Response response) {
        String uri = request.getUri();
        Command command = commands.get(uri);
        
        if (command != null) {
            System.out.println("Dispatcher: Routing " + uri + " to " + command.getClass().getSimpleName());
            command.execute(request, response);
        } else {
            System.out.println("Dispatcher: No handler found for " + uri);
            response.setStatusCode(404);
            response.setContent("<html><body><h1>404 - Page Not Found</h1></body></html>");
        }
    }
    
    public void registerCommand(String uri, Command command) {
        commands.put(uri, command);
        System.out.println("Dispatcher: Registered command for " + uri);
    }
}