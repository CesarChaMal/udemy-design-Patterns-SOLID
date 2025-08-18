package com.balazsholczer.functional;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

/**
 * Minimal Free monad (pure + flatMap) for tests.
 * Only supports sequencing of pure computations (no external functor).
 */
public abstract class Free<T> {

    public abstract <R> Free<R> map(Function<T, R> f);

    public abstract <R> Free<R> flatMap(Function<T, Free<R>> f);

    public abstract boolean isPure();

    /**
     * Returns the contained value if this is a Pure node else throws.
     */
    public T getValue() {
        throw new IllegalStateException("Not a pure value");
    }

    /**
     * Interpret (run) the free structure, reducing it to a single value.
     * Stack-safe via explicit continuation stack.
     */
    @SuppressWarnings("unchecked")
    public T interpret() {
        Free<?> current = this;
        Deque<Function<Object, Free<?>>> conts = new ArrayDeque<>();

        while (true) {
            if (current instanceof Pure<?> p) {
                Object v = p.value;
                if (conts.isEmpty()) {
                    return (T) v;
                }
                Function<Object, Free<?>> k = conts.pop();
                current = k.apply(v);
            } else if (current instanceof FlatMapped<?, ?> fm) {
                // Unwrap left side; defer its continuation
                conts.push((Function<Object, Free<?>>) fm.next);
                current = fm.prev;
            } else {
                throw new IllegalStateException("Unknown Free node");
            }
        }
    }

    /* Factory */
    public static <T> Free<T> pure(T value) {
        return new Pure<>(value);
    }

    /* Internal variants */

    private static final class Pure<T> extends Free<T> {
        private final T value;

        private Pure(T value) {
            this.value = value;
        }

        @Override
        public <R> Free<R> map(Function<T, R> f) {
            return new Pure<>(f.apply(value));
        }

        @Override
        public <R> Free<R> flatMap(Function<T, Free<R>> f) {
            return new FlatMapped<>(this, f);
        }

        @Override
        public boolean isPure() {
            return true;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Pure(" + value + ")";
        }
    }

    private static final class FlatMapped<A, B> extends Free<B> {
        private final Free<A> prev;
        private final Function<A, Free<B>> next;

        private FlatMapped(Free<A> prev, Function<A, Free<B>> next) {
            this.prev = prev;
            this.next = next;
        }

        @Override
        public <R> Free<R> map(Function<B, R> f) {
            return new FlatMapped<>(prev, a -> next.apply(a).map(f));
        }

        @Override
        public <R> Free<R> flatMap(Function<B, Free<R>> f) {
            return new FlatMapped<>(prev, a -> next.apply(a).flatMap(f));
        }

        @Override
        public boolean isPure() {
            return false;
        }

        @Override
        public String toString() {
            return "FlatMapped(" + prev + ", <fn>)";
        }
    }
}