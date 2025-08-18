package com.balazsholczer.valuelisthandler;

import java.util.List;

public class ValueList<T> {
    private final List<T> data;
    private final int totalCount;
    private final int pageSize;
    private final int currentPage;
    
    public ValueList(List<T> data, int totalCount, int pageSize, int currentPage) {
        this.data = data;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
    
    public List<T> getData() { return data; }
    public int getTotalCount() { return totalCount; }
    public int getPageSize() { return pageSize; }
    public int getCurrentPage() { return currentPage; }
    public int getTotalPages() { return (int) Math.ceil((double) totalCount / pageSize); }
    public boolean hasNext() { return currentPage < getTotalPages(); }
    public boolean hasPrevious() { return currentPage > 1; }
}