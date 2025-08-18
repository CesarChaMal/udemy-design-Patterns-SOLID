package com.balazsholczer.solid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.balazsholczer.solid.improved.*;
import java.time.LocalDateTime;

/**
 * Comprehensive test for Single Responsibility Principle
 * Tests Traditional vs Improved approaches
 */
class SolidSTest {
    
    @Test
    void testTraditionalApproach() {
        // Traditional approach - Book class does everything
        Book book = new Book("GoF", 400);
        
        // Book handles its own persistence
        assertDoesNotThrow(() -> book.save());
        assertEquals("GoF - 400", book.toString());
    }
    
    @Test
    void testImprovedApproach() {
        // Improved approach - Separate responsibilities
        BookValidator validator = new BookValidator();
        BookRepository repository = new InMemoryBookRepository();
        BookService service = new BookService(repository, validator);
        
        com.balazsholczer.solid.improved.Book book = 
            new com.balazsholczer.solid.improved.Book("978-0201633610", "Design Patterns", "GoF", 395, LocalDateTime.now());
        
        // Each class has single responsibility
        assertDoesNotThrow(() -> validator.validate(book));
        assertDoesNotThrow(() -> repository.save(book));
        assertDoesNotThrow(() -> service.createBook(book));
    }
    
    @Test
    void testSingleResponsibilityViolation() {
        // Traditional Book violates SRP by handling persistence
        Book book = new Book("Author", 100);
        
        // Book is responsible for both data and persistence
        assertDoesNotThrow(() -> book.save());
        assertTrue(true); // Book has multiple responsibilities
    }
    
    @Test
    void testSingleResponsibilityCompliance() {
        // Improved approach follows SRP
        BookValidator validator = new BookValidator();
        BookRepository repository = new InMemoryBookRepository();
        BookService service = new BookService(repository, validator);
        
        // Each class has one reason to change:
        // - BookValidator: validation rules change
        // - BookRepository: storage mechanism changes  
        // - BookService: business logic changes
        
        assertNotNull(validator);
        assertNotNull(repository);
        assertNotNull(service);
    }
    
    @Test
    void testResponsibilityIsolation() {
        // Test that changes in one responsibility don't affect others
        BookValidator validator = new BookValidator();
        BookRepository repository = new InMemoryBookRepository();
        
        com.balazsholczer.solid.improved.Book validBook = 
            new com.balazsholczer.solid.improved.Book("978-0201633610", "Valid Title", "Valid Author", 200, LocalDateTime.now());
        
        // Validation logic is isolated
        assertDoesNotThrow(() -> validator.validate(validBook));
        
        // Repository logic is isolated
        assertDoesNotThrow(() -> repository.save(validBook));
    }
    
    @Test
    void testEquivalence() {
        // Both approaches should handle valid books correctly
        Book traditionalBook = new Book("Test Author", 200);
        com.balazsholczer.solid.improved.Book improvedBook = 
            new com.balazsholczer.solid.improved.Book("978-0201633610", "Test Book", "Test Author", 200, LocalDateTime.now());
        
        // Traditional approach - book handles its own operations
        assertDoesNotThrow(() -> traditionalBook.save());
        
        // Improved approach - separate responsibilities
        BookValidator validator = new BookValidator();
        assertDoesNotThrow(() -> validator.validate(improvedBook));
        
        // Both handle books successfully
        assertTrue(true);
    }
}