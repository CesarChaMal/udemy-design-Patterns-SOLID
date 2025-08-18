package com.balazsholczer.frontcontroller;

public class FrontController {
    
    private final Dispatcher dispatcher;
    
    public FrontController() {
        this.dispatcher = new Dispatcher();
    }
    
    public Response processRequest(Request request) {
        System.out.println("FrontController: Processing request - " + request);
        
        // Pre-processing
        preProcess(request);
        
        // Dispatch to appropriate handler
        Response response = new Response();
        dispatcher.dispatch(request, response);
        
        // Post-processing
        postProcess(request, response);
        
        System.out.println("FrontController: Request processed - " + response);
        return response;
    }
    
    private void preProcess(Request request) {
        System.out.println("FrontController: Pre-processing request");
        // Add common request attributes, security checks, logging, etc.
        request.setAttribute("requestTime", System.currentTimeMillis());
    }
    
    private void postProcess(Request request, Response response) {
        System.out.println("FrontController: Post-processing response");
        // Add common response headers, logging, cleanup, etc.
        long requestTime = (Long) request.getAttribute("requestTime");
        long processingTime = System.currentTimeMillis() - requestTime;
        System.out.println("FrontController: Processing time: " + processingTime + "ms");
    }
    
    public void registerCommand(String uri, Command command) {
        dispatcher.registerCommand(uri, command);
    }
}