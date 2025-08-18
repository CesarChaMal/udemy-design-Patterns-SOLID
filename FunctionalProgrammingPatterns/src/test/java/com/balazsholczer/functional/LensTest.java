package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LensTest {

    static class Person {
        private final String name;
        private final int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        
        public Person withName(String newName) {
            return new Person(newName, age);
        }
        
        public Person withAge(int newAge) {
            return new Person(name, newAge);
        }
    }

    @Test
    void testLensGet() {
        Lens<Person, String> nameLens = Lens.of(Person::getName, person -> newName -> person.withName(newName));
        Person person = new Person("John", 30);
        
        assertEquals("John", nameLens.get(person));
    }

    @Test
    void testLensSet() {
        Lens<Person, String> nameLens = Lens.of(Person::getName, person -> newName -> person.withName(newName));
        Person person = new Person("John", 30);
        Person updated = nameLens.set(person, "Jane");
        
        assertEquals("Jane", updated.getName());
        assertEquals(30, updated.getAge());
    }

    @Test
    void testLensModify() {
        Lens<Person, String> nameLens = Lens.of(Person::getName, person -> newName -> person.withName(newName));
        Person person = new Person("john", 30);
        Person updated = nameLens.modify(person, String::toUpperCase);
        
        assertEquals("JOHN", updated.getName());
        assertEquals(30, updated.getAge());
    }

    @Test
    void testLensComposition() {
        Lens<Person, Integer> ageLens = Lens.of(Person::getAge, person -> newAge -> person.withAge(newAge));
        Person person = new Person("John", 30);
        
        Person older = ageLens.modify(person, age -> age + 1);
        assertEquals(31, older.getAge());
        assertEquals("John", older.getName());
    }
    
    @Test
    void testLensChaining() {
        Lens<Person, String> nameLens = Lens.of(Person::getName, person -> newName -> person.withName(newName));
        Lens<Person, Integer> ageLens = Lens.of(Person::getAge, person -> newAge -> person.withAge(newAge));
        
        Person person = new Person("John", 30);
        
        Person updated = nameLens.set(person, "Jane");
        Person aged = ageLens.set(updated, 25);
        
        assertEquals("Jane", aged.getName());
        assertEquals(25, aged.getAge());
    }
}