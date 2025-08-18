package com.balazsholczer.functional;

import java.util.function.Function;

public class Box<T> implements Functor<T> {
    private final T value;
    
    public Box(T value) {
        this.value = value;
    }
    
    public T getValue() {
        return value;
    }
    
    @Override
    public <R> Box<R> map(Function<T, R> f) {
        return new Box<>(f.apply(value));
    }
    
    @Override
    public String toString() {
        return "Box(" + value + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Box<?> box = (Box<?>) obj;
        return value != null ? value.equals(box.value) : box.value == null;
    }
    
    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}