package com.balazsholczer.iterator;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class FunctionalNameRepository {
    
    private final String[] names = {"Adam", "Joe", "John", "Sarah"};
    
    public void iterate(Consumer<String> action) {
        for (String name : names) {
            action.accept(name);
        }
    }
    
    public void iterateIf(Predicate<String> condition, Consumer<String> action) {
        for (String name : names) {
            if (condition.test(name)) {
                action.accept(name);
            }
        }
    }
    
    public String[] getNames() {
        return names.clone();
    }
}