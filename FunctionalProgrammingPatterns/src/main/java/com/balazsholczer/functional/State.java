package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * State Monad Pattern - stateful computations
 */
@FunctionalInterface
public interface State<S, A> {
    Pair<A, S> run(S state);
    
    default <B> State<S, B> map(Function<A, B> mapper) {
        return state -> {
            Pair<A, S> result = run(state);
            return new Pair<>(mapper.apply(result.first), result.second);
        };
    }
    
    default <B> State<S, B> flatMap(Function<A, State<S, B>> mapper) {
        return state -> {
            Pair<A, S> result = run(state);
            return mapper.apply(result.first).run(result.second);
        };
    }
    
    static <S, A> State<S, A> pure(A value) {
        return state -> new Pair<>(value, state);
    }
    
    static <S> State<S, S> get() {
        return state -> new Pair<>(state, state);
    }
    
    static <S> State<S, Void> put(S newState) {
        return state -> new Pair<>(null, newState);
    }
    
    record Pair<A, B>(A first, B second) {}
}