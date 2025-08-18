package com.balazsholczer.filter;

public class AuthenticationFilter implements Filter {
    
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("AuthenticationFilter: Checking authentication");
        
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("AuthenticationFilter: User authenticated");
            request.setAttribute("user", "authenticated_user");
            chain.doFilter(request, response);
        } else {
            System.out.println("AuthenticationFilter: Authentication failed");
            response.setStatusCode(401);
            response.setContent("Unauthorized");
        }
    }
}