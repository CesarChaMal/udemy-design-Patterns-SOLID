package com.balazsholczer.pagecontroller;

public class HomePageController implements PageController {
    
    @Override
    public HttpResponse handleRequest(HttpRequest request) {
        System.out.println("HomePageController: Handling " + request);
        
        String content = "<html><body><h1>Welcome to Home Page</h1>" +
                        "<p>Hello, " + request.getParameter("user") + "!</p></body></html>";
        
        return new HttpResponse(200, content, "text/html");
    }
}