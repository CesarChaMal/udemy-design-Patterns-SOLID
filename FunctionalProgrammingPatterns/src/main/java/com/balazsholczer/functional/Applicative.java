package com.balazsholczer.functional;

import java.util.function.Function;

public class Applicative<T> {
    private final T value;
    
    public Applicative(T value) {
        this.value = value;
    }
    
    public T getValue() {
        return value;
    }
    
    public <R> Applicative<R> map(Function<T, R> f) {
        return new Applicative<>(f.apply(value));
    }
    
    public <R> Applicative<R> apply(Applicative<Function<T, R>> af) {
        return af.map(f -> f.apply(value));
    }
    
    public static <T> Applicative<T> pure(T value) {
        return new Applicative<>(value);
    }
    
    @Override
    public String toString() {
        return "Applicative(" + value + ")";
    }
}