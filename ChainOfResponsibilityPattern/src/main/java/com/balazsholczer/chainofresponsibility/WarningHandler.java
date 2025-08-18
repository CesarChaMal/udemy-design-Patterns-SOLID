package com.balazsholczer.chainofresponsibility;

public class WarningHandler extends Handler {
    
    @Override
    public void handleRequest(Request request) {
        if ("WARNING".equals(request.getType())) {
            System.out.println("WarningHandler: Processing " + request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("No handler found for: " + request);
        }
    }
}