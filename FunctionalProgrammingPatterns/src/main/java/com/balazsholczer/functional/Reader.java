package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Reader Monad Pattern - dependency injection
 */
@FunctionalInterface
public interface Reader<R, A> {
    A run(R environment);

    default <B> Reader<R, B> map(Function<A, B> mapper) {
        return env -> mapper.apply(run(env));
    }

    default <B> Reader<R, B> flatMap(Function<A, Reader<R, B>> mapper) {
        return env -> mapper.apply(run(env)).run(env);
    }

    static <R, A> Reader<R, A> pure(A value) {
        return env -> value;
    }

    static <R> Reader<R, R> ask() {
        return env -> env;
    }

    static <R, A> Reader<R, A> local(Function<R, R> modifier, Reader<R, A> reader) {
        return env -> reader.run(modifier.apply(env));
    }
}
