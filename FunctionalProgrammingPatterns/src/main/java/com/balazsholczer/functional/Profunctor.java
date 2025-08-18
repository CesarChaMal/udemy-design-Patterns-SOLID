package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Profunctor: contravariant in the input (A) and covariant in the output (B).
 */
public final class Profunctor<A, B> {
    private final Function<A, B> fn;

    private Profunctor(Function<A, B> fn) {
        this.fn = fn;
    }

    public static <A, B> Profunctor<A, B> of(Function<A, B> fn) {
        return new Profunctor<>(fn);
    }

    public B apply(A a) {
        return fn.apply(a);
    }

    /** Alias required by tests. */
    public B run(A a) {
        return apply(a);
    }

    /** Map input (contravariant side). */
    public <C> Profunctor<C, B> lmap(Function<? super C, ? extends A> l) {
        return new Profunctor<>(c -> fn.apply(l.apply(c)));
    }

    /** Map output (covariant side). */
    public <D> Profunctor<A, D> rmap(Function<? super B, ? extends D> r) {
        return new Profunctor<>(a -> r.apply(fn.apply(a)));
    }

    /** Map both sides. */
    public <C, D> Profunctor<C, D> dimap(Function<? super C, ? extends A> l,
                                         Function<? super B, ? extends D> r) {
        return new Profunctor<>(c -> r.apply(fn.apply(l.apply(c))));
    }

    @Override
    public String toString() {
        return "Profunctor";
    }
}