package com.balazsholczer.pagecontroller;

public class LoginPageController implements PageController {
    
    @Override
    public HttpResponse handleRequest(HttpRequest request) {
        System.out.println("LoginPageController: Handling " + request);
        
        if ("POST".equals(request.getMethod())) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if ("admin".equals(username) && "password".equals(password)) {
                String content = "<html><body><h1>Login Successful</h1>" +
                               "<p>Welcome, " + username + "!</p></body></html>";
                return new HttpResponse(200, content, "text/html");
            } else {
                String content = "<html><body><h1>Login Failed</h1>" +
                               "<p>Invalid credentials</p></body></html>";
                return new HttpResponse(401, content, "text/html");
            }
        } else {
            String content = "<html><body><h1>Login Page</h1>" +
                           "<form method='POST'>" +
                           "Username: <input name='username'/><br/>" +
                           "Password: <input name='password' type='password'/><br/>" +
                           "<input type='submit' value='Login'/>" +
                           "</form></body></html>";
            return new HttpResponse(200, content, "text/html");
        }
    }
}