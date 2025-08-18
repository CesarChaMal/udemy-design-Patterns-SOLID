package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Kleisli arrow for the Maybe monad.
 * Wraps a function A -> Maybe<B>.
 */
public final class Kleisli<A, B> {

    private final Function<A, Maybe<B>> fn;

    private Kleisli(Function<A, Maybe<B>> fn) {
        this.fn = fn;
    }

    public static <A, B> Kleisli<A, B> of(Function<A, Maybe<B>> fn) {
        return new Kleisli<>(fn);
    }

    /** Execute the underlying function. */
    public Maybe<B> apply(A a) {
        return fn.apply(a);
    }

    /** Alias required by tests. */
    public Maybe<B> run(A a) {
        return apply(a);
    }

    /** Functor map over the result inside Maybe. */
    public <C> Kleisli<A, C> map(Function<B, C> f) {
        return new Kleisli<>(a -> apply(a).map(f));
    }

    /** Monad bind at the Kleisli level (sequential composition on same input). */
    public <C> Kleisli<A, C> flatMap(Function<B, Kleisli<A, C>> f) {
        return new Kleisli<>(a -> apply(a).flatMap(b -> f.apply(b).apply(a)));
    }

    /** Compose: this: A -> M<B>, next: B -> M<C> giving A -> M<C>. */
    public <C> Kleisli<A, C> andThen(Kleisli<B, C> next) {
        return new Kleisli<>(a -> apply(a).flatMap(next::apply));
    }

    /** Pre-compose: prev: Z -> M<A>, this: A -> M<B> giving Z -> M<B>. */
    public <Z> Kleisli<Z, B> compose(Kleisli<Z, A> prev) {
        return new Kleisli<>(z -> prev.apply(z).flatMap(this::apply));
    }

    /** Lift a pure value into Kleisli (constant function). */
    public static <A, B> Kleisli<A, B> pure(B value) {
        return new Kleisli<>(a -> Maybe.of(value));
    }

    /** Lift a plain function into Kleisli via Maybe.some. */
    public static <A, B> Kleisli<A, B> lift(Function<A, B> f) {
        return new Kleisli<>(a -> Maybe.of(f.apply(a)));
    }

    @Override
    public String toString() {
        return "Kleisli";
    }
}