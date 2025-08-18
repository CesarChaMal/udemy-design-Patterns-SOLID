package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Traversable Pattern - traverse data structures with effects
 */
public interface Traversable<T> {
    <U> Maybe<ImmutableList<U>> traverse(Function<T, Maybe<U>> f);
    
    default <U> Maybe<ImmutableList<U>> mapM(Function<T, Maybe<U>> f) {
        return traverse(f);
    }
}