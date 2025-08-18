package com.balazsholczer.functional;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Maybe<T> {

    public abstract <U> Maybe<U> map(Function<T, U> mapper);
    public abstract <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper);
    public abstract Maybe<T> filter(Predicate<T> predicate);
    public abstract T orElse(T other);
    public abstract T orElseGet(Supplier<T> supplier);
    public abstract boolean isPresent();
    public abstract T get();

    public static <T> Maybe<T> of(T value) {
        return value == null ? none() : new Some<>(value);
    }

    public static <T> Maybe<T> some(T value) {
        if (value == null) throw new IllegalArgumentException("Some cannot hold null");
        return new Some<>(value);
    }

    public static <T> Maybe<T> none() {
        return None.instance();
    }

    private static final class Some<T> extends Maybe<T> {
        private final T value;

        private Some(T value) {
            this.value = value;
        }

        @Override
        public <U> Maybe<U> map(Function<T, U> mapper) {
            return of(mapper.apply(value));
        }

        @Override
        public <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper) {
            return mapper.apply(value);
        }

        @Override
        public Maybe<T> filter(Predicate<T> predicate) {
            return predicate.test(value) ? this : none();
        }

        @Override
        public T orElse(T other) {
            return value;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public String toString() {
            return "Some(" + value + ")";
        }
    }

    private static final class None<T> extends Maybe<T> {
        private static final None<?> INSTANCE = new None<>();

        @SuppressWarnings("unchecked")
        static <T> None<T> instance() {
            return (None<T>) INSTANCE;
        }

        private None() {}

        @Override
        public <U> Maybe<U> map(Function<T, U> mapper) {
            return instance();
        }

        @Override
        public <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper) {
            return instance();
        }

        @Override
        public Maybe<T> filter(Predicate<T> predicate) {
            return this;
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T get() {
            throw new NoSuchElementException("No value present in None");
        }

        @Override
        public String toString() {
            return "None";
        }
    }
}