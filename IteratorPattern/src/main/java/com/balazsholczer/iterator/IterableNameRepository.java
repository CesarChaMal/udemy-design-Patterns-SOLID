package com.balazsholczer.iterator;

import java.util.Arrays;
import java.util.List;

public class IterableNameRepository implements Iterable<String> {
    
    private final List<String> names = Arrays.asList("Adam", "Joe", "John", "Sarah");
    
    @Override
    public java.util.Iterator<String> iterator() {
        return names.iterator();
    }
    
    public List<String> getNames() {
        return names;
    }
}