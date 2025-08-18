package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Service layer for book business operations
 */
public class BookService {
    private final BookRepository repository;
    private final BookValidator validator;
    
    public BookService(BookRepository repository, BookValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    
    public CompletableFuture<Void> createBook(Book book) {
        return CompletableFuture.runAsync(() -> {
            validator.validate(book);
            System.out.println("BookService: Creating book " + book.getTitle());
        }).thenCompose(v -> repository.save(book));
    }
    
    public CompletableFuture<Optional<Book>> getBook(String isbn) {
        System.out.println("BookService: Getting book " + isbn);
        return repository.findByIsbn(isbn);
    }
    
    public CompletableFuture<Void> removeBook(String isbn) {
        System.out.println("BookService: Removing book " + isbn);
        return repository.delete(isbn);
    }
}