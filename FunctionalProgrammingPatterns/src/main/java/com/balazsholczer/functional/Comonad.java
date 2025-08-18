package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Comonad Pattern - dual of Monad, extracts values from context
 */
public abstract class Comonad<T> {
    
    public abstract T extract();
    public abstract <U> Comonad<U> map(Function<T, U> mapper);
    public abstract <U> Comonad<U> extend(Function<Comonad<T>, U> extractor);
    
    public static <T> Comonad<T> identity(T value) {
        return new Identity<>(value);
    }
    
    private static class Identity<T> extends Comonad<T> {
        private final T value;
        
        Identity(T value) {
            this.value = value;
        }
        
        @Override
        public T extract() {
            return value;
        }
        
        @Override
        public <U> Comonad<U> map(Function<T, U> mapper) {
            return new Identity<>(mapper.apply(value));
        }
        
        @Override
        public <U> Comonad<U> extend(Function<Comonad<T>, U> extractor) {
            return new Identity<>(extractor.apply(this));
        }
        
        @Override
        public String toString() {
            return "Identity(" + value + ")";
        }
    }
}