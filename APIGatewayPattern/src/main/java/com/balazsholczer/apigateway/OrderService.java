package com.balazsholczer.apigateway;

public class OrderService implements MicroService {
    
    @Override
    public Response handleRequest(Request request) {
        System.out.println("OrderService: Handling " + request);
        
        if (request.getPath().startsWith("/orders")) {
            return new Response(200, "{\"orders\": [{\"id\": 1, \"total\": 99.99}]}");
        }
        
        return new Response(404, "{\"error\": \"Not found\"}");
    }
    
    @Override
    public String getServiceName() {
        return "OrderService";
    }
}