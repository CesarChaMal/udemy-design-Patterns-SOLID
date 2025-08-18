package com.balazsholczer.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive test for DAO Pattern
 * Tests Traditional, Functional, Generic, and Stream approaches
 */
class DAOTest {
    
    private Database traditionalDAO;
    private FunctionalPersonDAO functionalDAO;
    private GenericDAO<PersonRecord, String> genericDAO;
    private StreamPersonRepository streamRepository;
    
    @BeforeEach
    void setUp() {
        traditionalDAO = new Database();
        functionalDAO = new FunctionalPersonDAO();
        genericDAO = new GenericDAO<>(PersonRecord::name);
        streamRepository = new StreamPersonRepository();
    }
    
    @Test
    void testTraditionalDAO() {
        Person person1 = new Person("John", 30);
        Person person2 = new Person("Jane", 25);
        
        // Insert operations
        traditionalDAO.insert(person1);
        traditionalDAO.insert(person2);
        
        // Verify insertions
        List<Person> people = traditionalDAO.getPeople();
        assertEquals(2, people.size());
        assertTrue(people.contains(person1));
        assertTrue(people.contains(person2));
        
        // Remove operation
        traditionalDAO.remove(person1);
        assertEquals(1, traditionalDAO.getPeople().size());
        assertFalse(traditionalDAO.getPeople().contains(person1));
        assertTrue(traditionalDAO.getPeople().contains(person2));
    }
    
    @Test
    void testFunctionalPersonDAO() {
        PersonRecord person1 = new PersonRecord("Alice", 28, "Female", "123 Main St");
        PersonRecord person2 = new PersonRecord("Bob", 32, "Male", "456 Oak Ave");
        
        // Save operations
        functionalDAO.save(person1);
        functionalDAO.save(person2);
        
        // Find operations
        Optional<PersonRecord> found = functionalDAO.findByName("Alice");
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().name());
        assertEquals(28, found.get().age());
        
        // Find all
        List<PersonRecord> all = functionalDAO.findAll();
        assertEquals(2, all.size());
        
        // Find with predicate
        List<PersonRecord> adults = functionalDAO.findWhere(p -> p.age() > 30);
        assertEquals(1, adults.size());
        assertEquals("Bob", adults.get(0).name());
        
        // Delete operation
        assertTrue(functionalDAO.delete("Alice"));
        assertEquals(1, functionalDAO.findAll().size());
        assertFalse(functionalDAO.findByName("Alice").isPresent());
    }
    
    @Test
    void testGenericDAO() {
        PersonRecord person1 = new PersonRecord("Charlie", 35, null, null);
        PersonRecord person2 = new PersonRecord("Diana", 29, null, null);
        
        // Save operations
        genericDAO.save(person1);
        genericDAO.save(person2);
        
        // Find by ID
        Optional<PersonRecord> found = genericDAO.findById("Charlie");
        assertTrue(found.isPresent());
        assertEquals("Charlie", found.get().name());
        
        // Find all
        List<PersonRecord> all = genericDAO.findAll();
        assertEquals(2, all.size());
        
        // Count
        assertEquals(2, genericDAO.count());
        
        // Find with condition
        List<PersonRecord> youngAdults = genericDAO.findWhere(p -> p.age() < 30);
        assertEquals(1, youngAdults.size());
        assertEquals("Diana", youngAdults.get(0).name());
        
        // Delete
        assertTrue(genericDAO.deleteById("Charlie"));
        assertEquals(1, genericDAO.count());
    }
    
    @Test
    void testStreamPersonRepository() {
        PersonRecord person1 = new PersonRecord("Eve", 26, null, null);
        PersonRecord person2 = new PersonRecord("Frank", 34, null, null);
        PersonRecord person3 = new PersonRecord("Grace", 28, null, null);
        
        // Save operations (fluent interface)
        streamRepository.save(person1)
                       .save(person2)
                       .save(person3);
        
        // Find by age
        List<PersonRecord> age28 = streamRepository.findByAge(28);
        assertEquals(1, age28.size());
        assertEquals("Grace", age28.get(0).name());
        
        // Find by age range
        List<PersonRecord> youngAdults = streamRepository.findByAgeRange(25, 30);
        assertEquals(2, youngAdults.size());
        
        // Average age
        double avgAge = streamRepository.averageAge();
        assertEquals(29.33, avgAge, 0.1);
        
        // Group by age
        var groupedByAge = streamRepository.groupByAge();
        assertTrue(groupedByAge.containsKey(26));
        assertTrue(groupedByAge.containsKey(34));
        assertTrue(groupedByAge.containsKey(28));
        
        // Find all sorted
        List<PersonRecord> sorted = streamRepository.findAllSorted();
        assertEquals("Eve", sorted.get(0).name());
        assertEquals("Frank", sorted.get(1).name());
        assertEquals("Grace", sorted.get(2).name());
    }
    
    @Test
    void testPersonRecordImmutability() {
        PersonRecord original = new PersonRecord("Test", 25, null, null);
        PersonRecord withGender = original.withGender("Male");
        PersonRecord withAddress = withGender.withAddress("789 Pine St");
        
        // Original should be unchanged
        assertNull(original.gender());
        assertNull(original.address());
        
        // New records should have updates
        assertEquals("Male", withGender.gender());
        assertNull(withGender.address());
        
        assertEquals("Male", withAddress.gender());
        assertEquals("789 Pine St", withAddress.address());
    }
    
    @Test
    void testEquivalentOperations() {
        // Test that all DAO approaches can perform equivalent operations
        
        // Traditional approach
        Person traditionalPerson = new Person("Traditional", 30);
        traditionalDAO.insert(traditionalPerson);
        
        // Functional approach
        PersonRecord functionalPerson = new PersonRecord("Functional", 30, null, null);
        functionalDAO.save(functionalPerson);
        
        // Generic approach
        PersonRecord genericPerson = new PersonRecord("Generic", 30, null, null);
        genericDAO.save(genericPerson);
        
        // Stream approach
        PersonRecord streamPerson = new PersonRecord("Stream", 30, null, null);
        streamRepository.save(streamPerson);
        
        // All should have one person
        assertEquals(1, traditionalDAO.getPeople().size());
        assertEquals(1, functionalDAO.findAll().size());
        assertEquals(1, genericDAO.findAll().size());
        assertEquals(1, streamRepository.findAll().size());
        
        // All should find their respective persons
        assertEquals("Traditional", traditionalDAO.getPeople().get(0).getName());
        assertTrue(functionalDAO.findByName("Functional").isPresent());
        assertTrue(genericDAO.findById("Generic").isPresent());
        assertTrue(streamRepository.findByName("Stream").isPresent());
    }
    
    @Test
    void testAdvancedStreamOperations() {
        // Test advanced stream-based operations
        List<PersonRecord> people = List.of(
            new PersonRecord("Alice", 25, "Female", "City A"),
            new PersonRecord("Bob", 30, "Male", "City B"),
            new PersonRecord("Charlie", 25, "Male", "City A"),
            new PersonRecord("Diana", 35, "Female", "City C")
        );
        
        streamRepository.saveAll(people);
        
        // Test grouping
        var ageGroups = streamRepository.groupByAge();
        assertEquals(2, ageGroups.get(25).size());
        assertEquals(1, ageGroups.get(30).size());
        assertEquals(1, ageGroups.get(35).size());
        
        // Test average calculation
        double avgAge = streamRepository.averageAge();
        assertEquals(28.75, avgAge, 0.01);
        
        // Test range queries
        List<PersonRecord> youngAdults = streamRepository.findByAgeRange(25, 30);
        assertEquals(3, youngAdults.size());
    }
    
    @Test
    void testDataIntegrity() {
        // Test that each DAO maintains data integrity
        
        // Traditional DAO with mutable objects
        Person mutablePerson = new Person("Mutable", 25);
        traditionalDAO.insert(mutablePerson);
        mutablePerson.setAge(26); // Modify after insertion
        assertEquals(26, traditionalDAO.getPeople().get(0).getAge());
        
        // Functional DAO with immutable records
        PersonRecord immutablePerson = new PersonRecord("Immutable", 25, null, null);
        functionalDAO.save(immutablePerson);
        // Cannot modify immutablePerson after creation
        assertEquals(25, functionalDAO.findByName("Immutable").get().age());
    }
}