package com.balazsholczer.chainofresponsibility;

public class ErrorHandler extends Handler {
    
    @Override
    public void handleRequest(Request request) {
        if ("ERROR".equals(request.getType())) {
            System.out.println("ErrorHandler: Processing " + request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("No handler found for: " + request);
        }
    }
}