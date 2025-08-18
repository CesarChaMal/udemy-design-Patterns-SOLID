package com.balazsholczer.functional;

import java.util.function.Function;

public class Lens<S, A> {
    private final Function<S, A> getter;
    private final Function<S, Function<A, S>> setter;
    
    public Lens(Function<S, A> getter, Function<S, Function<A, S>> setter) {
        this.getter = getter;
        this.setter = setter;
    }
    
    public A get(S s) {
        return getter.apply(s);
    }
    
    public S set(S s, A a) {
        return setter.apply(s).apply(a);
    }
    
    public S modify(S s, Function<A, A> f) {
        return set(s, f.apply(get(s)));
    }
    
    public <B> Lens<S, B> compose(Lens<A, B> other) {
        return new Lens<>(
            s -> other.get(get(s)),
            s -> b -> set(s, other.set(get(s), b))
        );
    }
    
    public static <S, A> Lens<S, A> of(Function<S, A> getter, Function<S, Function<A, S>> setter) {
        return new Lens<>(getter, setter);
    }
    
    // Alias factory matching usage in AppAdvanced: Lens.lens(getter, setter)
    public static <S, A> Lens<S, A> lens(Function<S, A> getter, Function<S, Function<A, S>> setter) {
        return new Lens<>(getter, setter);
    }
}