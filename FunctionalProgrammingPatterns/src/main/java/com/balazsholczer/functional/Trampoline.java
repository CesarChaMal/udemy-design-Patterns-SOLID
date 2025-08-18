package com.balazsholczer.functional;

import java.util.function.Supplier;

public abstract class Trampoline<T> {
    
    public abstract boolean isComplete();
    public abstract T get();
    public abstract Trampoline<T> next();
    
    public final T run() {
        Trampoline<T> current = this;
        while (!current.isComplete()) {
            current = current.next();
        }
        return current.get();
    }
    
    public static <T> Trampoline<T> done(T value) {
        return new Done<>(value);
    }
    
    public static <T> Trampoline<T> more(Supplier<Trampoline<T>> next) {
        return new More<>(next);
    }
    
    private static class Done<T> extends Trampoline<T> {
        private final T value;
        
        Done(T value) {
            this.value = value;
        }
        
        @Override
        public boolean isComplete() {
            return true;
        }
        
        @Override
        public T get() {
            return value;
        }
        
        @Override
        public Trampoline<T> next() {
            throw new UnsupportedOperationException("Done trampoline has no next");
        }
    }
    
    private static class More<T> extends Trampoline<T> {
        private final Supplier<Trampoline<T>> next;
        
        More(Supplier<Trampoline<T>> next) {
            this.next = next;
        }
        
        @Override
        public boolean isComplete() {
            return false;
        }
        
        @Override
        public T get() {
            throw new UnsupportedOperationException("More trampoline is not complete");
        }
        
        @Override
        public Trampoline<T> next() {
            return next.get();
        }
    }
}