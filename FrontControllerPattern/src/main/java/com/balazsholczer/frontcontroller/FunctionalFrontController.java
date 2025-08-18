package com.balazsholczer.frontcontroller;

import java.util.Map;
import java.util.function.BiConsumer;

public class FunctionalFrontController {
    
    private final Map<String, BiConsumer<Request, Response>> handlers;
    
    public FunctionalFrontController() {
        this.handlers = Map.of(
            "/", (req, res) -> {
                System.out.println("Functional: Home handler");
                res.setContent("<html><body><h1>Functional Home</h1></body></html>");
            },
            "/api/users", (req, res) -> {
                System.out.println("Functional: API users handler");
                res.setContent("{\"users\": [\"john\", \"jane\"]}");
                res.setContentType("application/json");
            },
            "/api/orders", (req, res) -> {
                System.out.println("Functional: API orders handler");
                res.setContent("{\"orders\": []}");
                res.setContentType("application/json");
            }
        );
    }
    
    public Response processRequest(Request request) {
        System.out.println("FunctionalFrontController: Processing " + request);
        
        Response response = new Response();
        BiConsumer<Request, Response> handler = handlers.get(request.getUri());
        
        if (handler != null) {
            handler.accept(request, response);
        } else {
            response.setStatusCode(404);
            response.setContent("{\"error\": \"Not Found\"}");
            response.setContentType("application/json");
        }
        
        return response;
    }
}