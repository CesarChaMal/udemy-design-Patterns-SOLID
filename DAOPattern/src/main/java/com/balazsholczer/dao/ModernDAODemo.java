package com.balazsholczer.dao;

import java.util.List;

public class ModernDAODemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional DAO Pattern ===");
        Database traditional = new Database();
        traditional.insert(new Person("John", 27));
        traditional.insert(new Person("Adam", 47));
        traditional.insert(new Person("Joe", 37));
        
        for (Person person : traditional.getPeople()) {
            System.out.println(person);
        }
        
        System.out.println("\n=== Functional DAO Pattern ===");
        FunctionalPersonDAO functional = new FunctionalPersonDAO();
        functional.save(new PersonRecord("John", 27));
        functional.save(new PersonRecord("Adam", 47));
        functional.save(new PersonRecord("Joe", 37));
        
        functional.findAll().forEach(System.out::println);
        
        System.out.println("\n=== Generic DAO Pattern ===");
        GenericDAO<PersonRecord, String> generic = new GenericDAO<>(PersonRecord::name);
        generic.save(new PersonRecord("John", 27));
        generic.save(new PersonRecord("Adam", 47));
        generic.save(new PersonRecord("Joe", 37));
        
        generic.findAll().forEach(System.out::println);
        
        System.out.println("\n=== Stream Repository Pattern ===");
        StreamPersonRepository stream = new StreamPersonRepository();
        stream.saveAll(List.of(
            new PersonRecord("John", 27),
            new PersonRecord("Adam", 47),
            new PersonRecord("Joe", 37),
            new PersonRecord("Jane", 27)
        ));
        
        stream.findAllSorted().forEach(System.out::println);
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - Find by condition:");
        functional.findWhere(p -> p.age() > 30).forEach(System.out::println);
        
        System.out.println("Generic - Count and find by ID:");
        System.out.println("Total count: " + generic.count());
        generic.findById("John").ifPresent(System.out::println);
        
        System.out.println("Stream - Group by age:");
        stream.groupByAge().forEach((age, people) -> 
            System.out.println("Age " + age + ": " + people.size() + " people"));
        
        System.out.println("Stream - Average age: " + stream.averageAge());
        
        System.out.println("Stream - Age range 25-35:");
        stream.findByAgeRange(25, 35).forEach(System.out::println);
    }
}