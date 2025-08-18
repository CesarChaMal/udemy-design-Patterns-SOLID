package com.balazsholczer.functional;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * IO Monad Pattern - pure functional side effects
 */
@FunctionalInterface
public interface IO<T> {
    T unsafeRun();
    
    default <U> IO<U> map(Function<T, U> mapper) {
        return () -> mapper.apply(unsafeRun());
    }
    
    default <U> IO<U> flatMap(Function<T, IO<U>> mapper) {
        return () -> mapper.apply(unsafeRun()).unsafeRun();
    }
    
    static <T> IO<T> pure(T value) {
        return () -> value;
    }
    
    static <T> IO<T> delay(Supplier<T> supplier) {
        return supplier::get;
    }
    
    static IO<Void> println(String message) {
        return () -> {
            System.out.println(message);
            return null;
        };
    }
    
    static IO<String> readLine() {
        return () -> {
            try {
                return new java.util.Scanner(System.in).nextLine();
            } catch (Exception e) {
                return "";
            }
        };
    }
}