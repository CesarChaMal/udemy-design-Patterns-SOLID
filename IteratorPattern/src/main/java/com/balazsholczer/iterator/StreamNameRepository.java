package com.balazsholczer.iterator;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamNameRepository {
    
    private final String[] names = {"Adam", "Joe", "John", "Sarah"};
    
    public Stream<String> stream() {
        return Arrays.stream(names);
    }
    
    public void forEach() {
        stream().forEach(System.out::println);
    }
    
    public void forEachFiltered(String prefix) {
        stream()
            .filter(name -> name.startsWith(prefix))
            .forEach(System.out::println);
    }
}