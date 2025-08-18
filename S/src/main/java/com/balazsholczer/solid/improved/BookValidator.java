package com.balazsholczer.solid.improved;

/**
 * Validator for book business rules
 */
public class BookValidator {
    
    public void validate(Book book) {
        validateIsbn(book.getIsbn());
        validateTitle(book.getTitle());
        validateAuthor(book.getAuthor());
        validatePages(book.getPages());
        System.out.println("BookValidator: Book validation passed");
    }
    
    private void validateIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        if (!isbn.matches("\\d{3}-\\d{10}")) {
            throw new IllegalArgumentException("Invalid ISBN format");
        }
    }
    
    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (title.length() > 200) {
            throw new IllegalArgumentException("Title too long");
        }
    }
    
    private void validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
    }
    
    private void validatePages(int pages) {
        if (pages < 1 || pages > 10000) {
            throw new IllegalArgumentException("Invalid page count");
        }
    }
}