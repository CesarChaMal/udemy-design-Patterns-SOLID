package com.balazsholczer.solid.improved;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modern Book entity with immutable design and validation
 */
public final class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final int pages;
    private final LocalDateTime publishedDate;
    
    public Book(String isbn, String title, String author, int pages, LocalDateTime publishedDate) {
        this.isbn = Objects.requireNonNull(isbn, "ISBN cannot be null");
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.author = Objects.requireNonNull(author, "Author cannot be null");
        this.pages = validatePages(pages);
        this.publishedDate = Objects.requireNonNull(publishedDate, "Published date cannot be null");
    }
    
    private int validatePages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Pages must be positive");
        }
        return pages;
    }
    
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPages() { return pages; }
    public LocalDateTime getPublishedDate() { return publishedDate; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
    
    @Override
    public String toString() {
        return String.format("Book{isbn='%s', title='%s', author='%s', pages=%d}", 
                           isbn, title, author, pages);
    }
}