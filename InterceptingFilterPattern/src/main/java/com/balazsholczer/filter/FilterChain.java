package com.balazsholczer.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {
    private final List<Filter> filters = new ArrayList<>();
    private int currentIndex = 0;
    
    public void addFilter(Filter filter) {
        filters.add(filter);
        System.out.println("FilterChain: Added " + filter.getClass().getSimpleName());
    }
    
    public void doFilter(Request request, Response response) {
        if (currentIndex < filters.size()) {
            Filter filter = filters.get(currentIndex++);
            filter.doFilter(request, response, this);
        } else {
            // End of chain - process actual request
            processRequest(request, response);
        }
    }
    
    private void processRequest(Request request, Response response) {
        System.out.println("FilterChain: Processing actual request - " + request);
        response.setContent("Processed: " + request.getUri());
    }
    
    public void reset() {
        currentIndex = 0;
    }
}