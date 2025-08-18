package com.balazsholczer.abstractfactory;

import java.util.Map;
import java.util.function.Supplier;

public class GenericAbstractFactory<T> {
    
    private final Map<Class<?>, Supplier<?>> factories;
    
    public GenericAbstractFactory(Map<Class<?>, Supplier<?>> factories) {
        this.factories = Map.copyOf(factories);
    }
    
    @SuppressWarnings("unchecked")
    public <U> U create(Class<U> type) {
        Supplier<?> factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("No factory for type: " + type.getName());
        }
        return (U) factory.get();
    }
    
    public static GenericAbstractFactory<?> windowsFactory() {
        return new GenericAbstractFactory<>(Map.of(
            Button.class, WindowsButton::new,
            Checkbox.class, WindowsCheckbox::new
        ));
    }
    
    public static GenericAbstractFactory<?> macFactory() {
        return new GenericAbstractFactory<>(Map.of(
            Button.class, MacButton::new,
            Checkbox.class, MacCheckbox::new
        ));
    }
}