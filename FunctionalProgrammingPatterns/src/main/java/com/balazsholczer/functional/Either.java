package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Either Monad Pattern - represents success or failure
 */
public abstract class Either<L, R> {

    public abstract <U> Either<L, U> map(Function<R, U> mapper);
    public abstract <U> Either<L, U> flatMap(Function<R, Either<L, U>> mapper);
    public abstract <U> Either<U, R> mapLeft(Function<L, U> mapper);
    public abstract boolean isLeft();
    public abstract boolean isRight();
    public abstract L getLeft();
    public abstract R getRight();

    public static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    private static final class Left<L, R> extends Either<L, R> {
        private final L value;

        Left(L value) {
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <U> Either<L, U> map(Function<R, U> mapper) {
            // ignore mapper, propagate same Left
            return (Either<L, U>) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <U> Either<L, U> flatMap(Function<R, Either<L, U>> mapper) {
            // propagate same Left
            return (Either<L, U>) this;
        }

        @Override
        public <U> Either<U, R> mapLeft(Function<L, U> mapper) {
            return new Left<>(mapper.apply(value));
        }

        @Override public boolean isLeft() { return true; }
        @Override public boolean isRight() { return false; }
        @Override public L getLeft() { return value; }
        @Override public R getRight() { throw new UnsupportedOperationException("Left has no right value"); }

        @Override
        public String toString() { return "Left(" + value + ")"; }
    }

    private static final class Right<L, R> extends Either<L, R> {
        private final R value;

        Right(R value) {
            this.value = value;
        }

        @Override
        public <U> Either<L, U> flatMap(Function<R, Either<L, U>> mapper) {
            return mapper.apply(value);
        }

        @Override
        public <U> Either<L, U> map(Function<R, U> mapper) {
            // IMPORTANT: keep the same L by constructing Right directly
            return new Right<>(mapper.apply(value));
        }

        @Override
        public <U> Either<U, R> mapLeft(Function<L, U> mapper) {
            // Still a Right; just change the L type parameter
            return new Right<>(value);
        }

        @Override public boolean isLeft() { return false; }
        @Override public boolean isRight() { return true; }
        @Override public L getLeft() { throw new UnsupportedOperationException("Right has no left value"); }
        @Override public R getRight() { return value; }

        @Override
        public String toString() { return "Right(" + value + ")"; }
    }
}
