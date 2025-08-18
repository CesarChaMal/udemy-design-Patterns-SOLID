package com.balazsholczer.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GenericDAO<T, ID> {
    
    private final Map<ID, T> storage = new ConcurrentHashMap<>();
    private final Function<T, ID> idExtractor;
    
    public GenericDAO(Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }
    
    public void save(T entity) {
        ID id = idExtractor.apply(entity);
        storage.put(id, entity);
    }
    
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }
    
    public List<T> findAll() {
        return List.copyOf(storage.values());
    }
    
    public List<T> findWhere(Predicate<T> condition) {
        return storage.values().stream()
                     .filter(condition)
                     .collect(Collectors.toList());
    }
    
    public boolean deleteById(ID id) {
        return storage.remove(id) != null;
    }
    
    public long count() {
        return storage.size();
    }
    
    public void clear() {
        storage.clear();
    }
}