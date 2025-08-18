package com.balazsholczer.prototype;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalPrototype {
    
    public record PrototypeFactory<T>(Supplier<T> creator, Function<T, T> cloner) {
        
        public T create() {
            T instance = creator.get();
            System.out.println("FunctionalPrototype: Created new instance");
            return instance;
        }
        
        public T clone(T original) {
            T cloned = cloner.apply(original);
            System.out.println("FunctionalPrototype: Cloned instance");
            return cloned;
        }
    }
    
    public static class PrototypeManager<T> {
        private final Map<String, PrototypeFactory<T>> factories = new ConcurrentHashMap<>();
        private final Map<String, T> prototypes = new ConcurrentHashMap<>();
        
        public void registerFactory(String key, PrototypeFactory<T> factory) {
            factories.put(key, factory);
            System.out.println("FunctionalPrototype: Registered factory " + key);
        }
        
        public void registerPrototype(String key, T prototype, Function<T, T> cloner) {
            prototypes.put(key, prototype);
            factories.put(key, new PrototypeFactory<>(() -> prototype, cloner));
            System.out.println("FunctionalPrototype: Registered prototype " + key);
        }
        
        public T create(String key) {
            PrototypeFactory<T> factory = factories.get(key);
            if (factory != null) {
                return factory.create();
            }
            throw new IllegalArgumentException("No factory found: " + key);
        }
        
        public T clone(String key) {
            T prototype = prototypes.get(key);
            PrototypeFactory<T> factory = factories.get(key);
            if (prototype != null && factory != null) {
                return factory.clone(prototype);
            }
            throw new IllegalArgumentException("No prototype found: " + key);
        }
    }
    
    // Common cloning functions
    public static final Function<String, String> STRING_CLONER = String::new;
    
    public static final Function<Book, Book> BOOK_CLONER = book -> (Book) book.clone();
    
    public static <T> Function<T, T> identityCloner() {
        return Function.identity();
    }
    
    public static <T> Function<T, T> deepCloner(Function<T, T> customCloner) {
        return customCloner;
    }
}