package com.balazsholczer.recordbuilder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecordBuilderTest {
    
    @Test
    public void testRecordBuilder() {
        App.Person person = App.Person.builder()
            .name("John Doe")
            .age(30)
            .email("john@example.com")
            .address("123 Main St")
            .build();
            
        assertEquals("John Doe", person.name());
        assertEquals(30, person.age());
        assertEquals("john@example.com", person.email());
        assertEquals("123 Main St", person.address());
    }
    
    @Test
    public void testBuilderFluentInterface() {
        App.Person person = App.Person.builder()
            .name("Jane Smith")
            .age(25)
            .build();
            
        assertEquals("Jane Smith", person.name());
        assertEquals(25, person.age());
    }
}