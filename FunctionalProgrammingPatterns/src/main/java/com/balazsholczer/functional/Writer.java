package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Writer Monad Pattern - computations with logging
 */
public class Writer<W, A> {
    private final A value;
    private final W log;
    
    public Writer(A value, W log) {
        this.value = value;
        this.log = log;
    }
    
    public A getValue() { return value; }
    public W getLog() { return log; }
    
    public <B> Writer<W, B> map(Function<A, B> mapper) {
        return new Writer<>(mapper.apply(value), log);
    }
    
    public <B> Writer<W, B> flatMap(Function<A, Writer<W, B>> mapper, Function<W, Function<W, W>> combiner) {
        Writer<W, B> result = mapper.apply(value);
        return new Writer<>(result.value, combiner.apply(log).apply(result.log));
    }
    
    public static <W, A> Writer<W, A> pure(A value, W emptyLog) {
        return new Writer<>(value, emptyLog);
    }
    
    public static <A> Writer<String, A> tell(A value, String message) {
        return new Writer<>(value, message);
    }
    
    @Override
    public String toString() {
        return "Writer(" + value + ", " + log + ")";
    }
}