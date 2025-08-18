package com.balazsholczer.builder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Builder Pattern
 * Tests Traditional, Functional, and Record approaches
 */
class BuilderTest {
    
    @Test
    void testTraditionalBuilder() {
        Person person = new Person.Builder("John", "john@example.com")
            .setAge(30)
            .setAddress("123 Main St")
            .build();
        
        assertNotNull(person);
        assertTrue(person.toString().contains("John"));
        assertTrue(person.toString().contains("john@example.com"));
    }
    
    @Test
    void testFunctionalBuilder() {
        PersonFunctional person = PersonFunctional.build("Jane", "jane@example.com", builder -> 
            builder.age(25).address("123 Main St")
        );
        
        assertNotNull(person);
        assertTrue(person.toString().contains("Jane"));
        assertTrue(person.toString().contains("jane@example.com"));
    }
    
    @Test
    void testRecordBuilder() {
        PersonRecord person = PersonRecord.of("Bob", "bob@example.com")
            .withAge(35)
            .withAddress("456 Oak Ave");
        
        assertEquals("Bob", person.name());
        assertEquals(35, person.age());
        assertEquals("bob@example.com", person.email());
        assertEquals("456 Oak Ave", person.address());
    }
    
    @Test
    void testBuilderChaining() {
        Person person = new Person.Builder("Test", "test@example.com")
            .setAge(25)
            .setAddress("Test Address")
            .setNameOfMother("Mother")
            .build();
        
        assertNotNull(person);
        String result = person.toString();
        assertTrue(result.contains("Test"));
        assertTrue(result.contains("test@example.com"));
    }
    
    @Test
    void testEquivalence() {
        String name = "Alice";
        int age = 28;
        String email = "alice@example.com";
        
        Person traditional = new Person.Builder(name, email)
            .setAge(age)
            .build();
        
        PersonFunctional functional = PersonFunctional.build(name, email, builder -> 
            builder.age(age)
        );
        
        PersonRecord record = PersonRecord.of(name, email)
            .withAge(age);
        
        // All should contain same values in toString
        String tradStr = traditional.toString();
        String funcStr = functional.toString();
        String recStr = record.toString();
        
        assertTrue(tradStr.contains(name));
        assertTrue(funcStr.contains(name));
        assertEquals(name, record.name());
        assertEquals(age, record.age());
        assertEquals(email, record.email());
    }
}