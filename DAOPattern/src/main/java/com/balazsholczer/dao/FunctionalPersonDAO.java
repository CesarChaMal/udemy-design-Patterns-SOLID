package com.balazsholczer.dao;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalPersonDAO {
    
    private final ConcurrentHashMap<String, PersonRecord> people = new ConcurrentHashMap<>();
    
    public void save(PersonRecord person) {
        people.put(person.name(), person);
    }
    
    public Optional<PersonRecord> findByName(String name) {
        return Optional.ofNullable(people.get(name));
    }
    
    public List<PersonRecord> findAll() {
        return List.copyOf(people.values());
    }
    
    public List<PersonRecord> findWhere(Predicate<PersonRecord> condition) {
        return people.values().stream()
                    .filter(condition)
                    .collect(Collectors.toList());
    }
    
    public boolean delete(String name) {
        return people.remove(name) != null;
    }
    
    public void clear() {
        people.clear();
    }
}