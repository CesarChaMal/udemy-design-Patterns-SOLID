package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * In-memory implementation of BookRepository
 */
public class InMemoryBookRepository implements BookRepository {
    private final ConcurrentMap<String, Book> books = new ConcurrentHashMap<>();
    
    @Override
    public CompletableFuture<Void> save(Book book) {
        return CompletableFuture.runAsync(() -> {
            books.put(book.getIsbn(), book);
            System.out.println("InMemoryBookRepository: Saved book " + book.getIsbn());
        });
    }
    
    @Override
    public CompletableFuture<Optional<Book>> findByIsbn(String isbn) {
        return CompletableFuture.supplyAsync(() -> {
            Book book = books.get(isbn);
            System.out.println("InMemoryBookRepository: Retrieved book " + isbn);
            return Optional.ofNullable(book);
        });
    }
    
    @Override
    public CompletableFuture<Void> delete(String isbn) {
        return CompletableFuture.runAsync(() -> {
            books.remove(isbn);
            System.out.println("InMemoryBookRepository: Deleted book " + isbn);
        });
    }
}