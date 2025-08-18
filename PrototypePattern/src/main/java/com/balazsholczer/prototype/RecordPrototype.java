package com.balazsholczer.prototype;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecordPrototype {
    
    public record BookRecord(String title, String author, int pages, String genre) {
        
        public BookRecord withTitle(String newTitle) {
            return new BookRecord(newTitle, author, pages, genre);
        }
        
        public BookRecord withAuthor(String newAuthor) {
            return new BookRecord(title, newAuthor, pages, genre);
        }
        
        public BookRecord withPages(int newPages) {
            return new BookRecord(title, author, newPages, genre);
        }
        
        public BookRecord withGenre(String newGenre) {
            return new BookRecord(title, author, pages, newGenre);
        }
        
        // Builder-style modifications
        public BookRecord modify(String newTitle, String newAuthor) {
            return new BookRecord(newTitle, newAuthor, pages, genre);
        }
    }
    
    public record PersonRecord(String name, int age, String email, List<String> hobbies) {
        
        public PersonRecord withName(String newName) {
            return new PersonRecord(newName, age, email, List.copyOf(hobbies));
        }
        
        public PersonRecord withAge(int newAge) {
            return new PersonRecord(name, newAge, email, List.copyOf(hobbies));
        }
        
        public PersonRecord withEmail(String newEmail) {
            return new PersonRecord(name, age, newEmail, List.copyOf(hobbies));
        }
        
        public PersonRecord withHobbies(List<String> newHobbies) {
            return new PersonRecord(name, age, email, List.copyOf(newHobbies));
        }
        
        public PersonRecord addHobby(String hobby) {
            var newHobbies = new java.util.ArrayList<>(hobbies);
            newHobbies.add(hobby);
            return new PersonRecord(name, age, email, newHobbies);
        }
    }
    
    // Registry for record prototypes
    public static class RecordRegistry<T extends Record> {
        private final Map<String, T> prototypes = new ConcurrentHashMap<>();
        
        public void register(String key, T prototype) {
            prototypes.put(key, prototype);
            System.out.println("RecordPrototype: Registered " + key);
        }
        
        public T get(String key) {
            T prototype = prototypes.get(key);
            if (prototype != null) {
                System.out.println("RecordPrototype: Retrieved " + key);
                return prototype; // Records are immutable, no need to clone
            }
            throw new IllegalArgumentException("No prototype found: " + key);
        }
        
        public List<String> getKeys() {
            return List.copyOf(prototypes.keySet());
        }
    }
}