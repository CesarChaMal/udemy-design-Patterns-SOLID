package com.balazsholczer.chainofresponsibility;

public class InfoHandler extends Handler {
    
    @Override
    public void handleRequest(Request request) {
        if ("INFO".equals(request.getType())) {
            System.out.println("InfoHandler: Processing " + request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("No handler found for: " + request);
        }
    }
}