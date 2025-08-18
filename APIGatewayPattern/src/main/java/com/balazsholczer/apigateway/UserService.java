package com.balazsholczer.apigateway;

public class UserService implements MicroService {
    
    @Override
    public Response handleRequest(Request request) {
        System.out.println("UserService: Handling " + request);
        
        if (request.getPath().startsWith("/users")) {
            return new Response(200, "{\"users\": [\"john\", \"jane\"]}");
        }
        
        return new Response(404, "{\"error\": \"Not found\"}");
    }
    
    @Override
    public String getServiceName() {
        return "UserService";
    }
}