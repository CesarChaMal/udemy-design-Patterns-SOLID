package com.balazsholczer.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Comprehensive test class for DAO Pattern - Traditional vs Modern approaches
 */
public class DAOPatternTest {
    
    private Database traditionalDatabase;
    private FunctionalPersonDAO functionalDAO;
    private StreamPersonRepository streamRepository;
    private GenericDAO<PersonRecord, String> genericDAO;
    
    @BeforeEach
    void setUp() {
        traditionalDatabase = new Database();
        functionalDAO = new FunctionalPersonDAO();
        streamRepository = new StreamPersonRepository();
        genericDAO = new GenericDAO<>(PersonRecord::name);
    }
    
    @Test
    void testTraditionalDAOPattern() {
        // Traditional DAO approach
        Person person1 = new Person("John", 27);
        Person person2 = new Person("Jane", 25);
        
        traditionalDatabase.insert(person1);
        traditionalDatabase.insert(person2);
        
        List<Person> people = traditionalDatabase.getPeople();
        assertEquals(2, people.size());
        assertTrue(people.contains(person1));
        assertTrue(people.contains(person2));
    }
    
    @Test
    void testFunctionalDAOPattern() {
        // Functional DAO approach
        PersonRecord person1 = new PersonRecord("Alice", 30, "Female", "123 Main St");
        PersonRecord person2 = new PersonRecord("Bob", 35, "Male", "456 Oak Ave");
        
        functionalDAO.save(person1);
        functionalDAO.save(person2);
        
        Optional<PersonRecord> found = functionalDAO.findByName("Alice");
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().name());
        
        List<PersonRecord> adults = functionalDAO.findWhere(p -> p.age() > 25);
        assertEquals(2, adults.size());
    }
    
    @Test
    void testStreamRepositoryPattern() {
        // Stream-based repository approach
        PersonRecord record1 = new PersonRecord("Charlie", 28, null, null);
        PersonRecord record2 = new PersonRecord("Diana", 32, null, null);
        
        streamRepository.save(record1);
        streamRepository.save(record2);
        
        List<PersonRecord> youngPeople = streamRepository.findByAgeRange(25, 30);
        assertEquals(1, youngPeople.size());
        assertEquals("Charlie", youngPeople.get(0).name());
        
        List<PersonRecord> allSorted = streamRepository.findAllSorted();
        assertEquals(2, allSorted.size());
        assertEquals("Charlie", allSorted.get(0).name());
    }
    
    @Test
    void testGenericDAOPattern() {
        // Generic DAO approach
        PersonRecord record1 = new PersonRecord("Eve", 29, null, null);
        PersonRecord record2 = new PersonRecord("Frank", 31, null, null);
        
        genericDAO.save(record1);
        genericDAO.save(record2);
        
        List<PersonRecord> all = genericDAO.findAll();
        assertEquals(2, all.size());
        
        Optional<PersonRecord> found = genericDAO.findById("Eve");
        assertTrue(found.isPresent());
        assertEquals("Eve", found.get().name());
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify all approaches handle same data equivalently
        String name = "TestUser";
        int age = 25;
        
        // Traditional
        Person traditionalPerson = new Person(name, age);
        traditionalDatabase.insert(traditionalPerson);
        
        // Functional
        PersonRecord functionalRecord = new PersonRecord(name, age, null, null);
        functionalDAO.save(functionalRecord);
        
        // Stream/Record
        PersonRecord record = new PersonRecord(name, age, null, null);
        streamRepository.save(record);
        
        // Generic
        PersonRecord genericRecord = new PersonRecord(name, age, null, null);
        genericDAO.save(genericRecord);
        
        // Verify equivalent results
        assertEquals(1, traditionalDatabase.getPeople().size());
        assertTrue(functionalDAO.findByName(name).isPresent());
        assertEquals(1, streamRepository.findByAgeRange(20, 30).size());
        assertEquals(1, genericDAO.findAll().size());
    }
    
    @Test
    void testDataAccessAbstraction() {
        // Test DAO pattern's core principle: data access abstraction
        Person person = new Person("DataTest", 40);
        PersonRecord record = new PersonRecord("DataTest", 40, null, null);
        
        // All DAOs should abstract data access details
        assertDoesNotThrow(() -> traditionalDatabase.insert(person));
        assertDoesNotThrow(() -> functionalDAO.save(record));
        assertDoesNotThrow(() -> streamRepository.save(record));
        assertDoesNotThrow(() -> genericDAO.save(record));
    }
    
    @Test
    void testCRUDOperations() {
        // Test Create, Read, Update, Delete operations
        Person person = new Person("CRUDTest", 35);
        PersonRecord record = new PersonRecord("CRUDTest", 35, null, null);
        
        // Create
        traditionalDatabase.insert(person);
        assertEquals(1, traditionalDatabase.getPeople().size());
        
        // Read
        List<Person> people = traditionalDatabase.getPeople();
        assertTrue(people.stream().anyMatch(p -> "CRUDTest".equals(p.getName())));
        
        // Functional approach CRUD
        functionalDAO.save(record);
        Optional<PersonRecord> found = functionalDAO.findByName("CRUDTest");
        assertTrue(found.isPresent());
        assertEquals(35, found.get().age());
        
        // Delete
        assertTrue(functionalDAO.delete("CRUDTest"));
        assertFalse(functionalDAO.findByName("CRUDTest").isPresent());
    }
}