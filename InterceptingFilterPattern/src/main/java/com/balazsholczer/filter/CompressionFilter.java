package com.balazsholczer.filter;

public class CompressionFilter implements Filter {
    
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("CompressionFilter: Pre-processing request");
        
        chain.doFilter(request, response);
        
        String acceptEncoding = request.getHeader("Accept-Encoding");
        if (acceptEncoding != null && acceptEncoding.contains("gzip")) {
            System.out.println("CompressionFilter: Compressing response");
            response.setHeader("Content-Encoding", "gzip");
            response.setContent("[COMPRESSED] " + response.getContent());
        }
    }
}