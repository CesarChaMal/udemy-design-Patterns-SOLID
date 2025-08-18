package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Arrow Pattern - generalization of functions with composition
 */
@FunctionalInterface
public interface Arrow<A, B> {
    B apply(A input);
    
    default <C> Arrow<A, C> compose(Arrow<B, C> other) {
        return input -> other.apply(apply(input));
    }
    
    default <C> Arrow<C, B> precompose(Arrow<C, A> other) {
        return input -> apply(other.apply(input));
    }
    
    static <T> Arrow<T, T> identity() {
        return x -> x;
    }
    
    static <A, B> Arrow<A, B> lift(Function<A, B> f) {
        return f::apply;
    }
}