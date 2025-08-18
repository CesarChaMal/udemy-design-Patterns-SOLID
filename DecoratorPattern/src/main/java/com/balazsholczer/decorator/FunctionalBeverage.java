package com.balazsholczer.decorator;

import java.util.function.Function;

@FunctionalInterface
public interface FunctionalBeverage extends Function<String, String> {
    
    static FunctionalBeverage plain() {
        return desc -> desc;
    }
    
    static FunctionalBeverage milk() {
        return desc -> desc + "milk ";
    }
    
    static FunctionalBeverage sugar() {
        return desc -> desc + "sugar ";
    }
    
    default FunctionalBeverage andThen(FunctionalBeverage after) {
        return desc -> after.apply(this.apply(desc));
    }
}