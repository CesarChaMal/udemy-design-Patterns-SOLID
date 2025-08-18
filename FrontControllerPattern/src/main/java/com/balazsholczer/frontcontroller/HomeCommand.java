package com.balazsholczer.frontcontroller;

public class HomeCommand implements Command {
    
    @Override
    public void execute(Request request, Response response) {
        System.out.println("HomeCommand: Processing home page request");
        response.setContent("<html><body><h1>Welcome to Home Page</h1></body></html>");
        response.setStatusCode(200);
    }
}