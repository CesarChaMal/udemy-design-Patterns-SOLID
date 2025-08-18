package com.balazsholczer.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamPersonRepository {
    
    private final Map<String, PersonRecord> people = new ConcurrentHashMap<>();
    
    public StreamPersonRepository save(PersonRecord person) {
        people.put(person.name(), person);
        return this;
    }
    
    public StreamPersonRepository saveAll(List<PersonRecord> persons) {
        persons.forEach(person -> people.put(person.name(), person));
        return this;
    }
    
    public Optional<PersonRecord> findByName(String name) {
        return Optional.ofNullable(people.get(name));
    }
    
    public List<PersonRecord> findByAge(int age) {
        return people.values().stream()
                    .filter(p -> p.age() == age)
                    .collect(Collectors.toList());
    }
    
    public List<PersonRecord> findByAgeRange(int minAge, int maxAge) {
        return people.values().stream()
                    .filter(p -> p.age() >= minAge && p.age() <= maxAge)
                    .collect(Collectors.toList());
    }
    
    public Map<Integer, List<PersonRecord>> groupByAge() {
        return people.values().stream()
                    .collect(Collectors.groupingBy(PersonRecord::age));
    }
    
    public double averageAge() {
        return people.values().stream()
                    .mapToInt(PersonRecord::age)
                    .average()
                    .orElse(0.0);
    }
    
    public List<PersonRecord> findAll() {
        return List.copyOf(people.values());
    }
    
    public List<PersonRecord> findAllSorted() {
        return people.values().stream()
                    .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                    .collect(Collectors.toList());
    }
}