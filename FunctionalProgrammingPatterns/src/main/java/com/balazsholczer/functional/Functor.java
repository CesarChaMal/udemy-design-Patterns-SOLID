package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Functor Pattern - containers that can be mapped over
 */
@FunctionalInterface
public interface Functor<T> {
    <U> Functor<U> map(Function<T, U> mapper);
}