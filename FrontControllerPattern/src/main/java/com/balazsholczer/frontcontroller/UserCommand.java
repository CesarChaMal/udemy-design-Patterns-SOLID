package com.balazsholczer.frontcontroller;

public class UserCommand implements Command {
    
    @Override
    public void execute(Request request, Response response) {
        System.out.println("UserCommand: Processing user request");
        String userId = request.getParameter("id");
        if (userId != null) {
            response.setContent("<html><body><h1>User Profile: " + userId + "</h1></body></html>");
        } else {
            response.setContent("<html><body><h1>User List</h1></body></html>");
        }
        response.setStatusCode(200);
    }
}