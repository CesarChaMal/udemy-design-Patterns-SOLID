package com.balazsholczer.filter;

public class LoggingFilter implements Filter {
    
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        long startTime = System.currentTimeMillis();
        System.out.println("LoggingFilter: Request started - " + request);
        
        chain.doFilter(request, response);
        
        long endTime = System.currentTimeMillis();
        System.out.println("LoggingFilter: Request completed in " + (endTime - startTime) + "ms - " + response.getStatusCode());
    }
}