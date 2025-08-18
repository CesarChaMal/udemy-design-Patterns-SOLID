package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Repository interface for book persistence operations
 */
public interface BookRepository {
    CompletableFuture<Void> save(Book book);
    CompletableFuture<Optional<Book>> findByIsbn(String isbn);
    CompletableFuture<Void> delete(String isbn);
}