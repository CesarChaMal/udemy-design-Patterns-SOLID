package com.balazsholczer.prototype;

public class Book implements Prototype {
    
    private String title;
    private String author;
    private int pages;
    private String genre;
    
    public Book(String title, String author, int pages, String genre) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genre = genre;
    }
    
    // Copy constructor for cloning
    private Book(Book other) {
        this.title = other.title;
        this.author = other.author;
        this.pages = other.pages;
        this.genre = other.genre;
    }
    
    @Override
    public Prototype clone() {
        return new Book(this);
    }
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', pages=" + pages + ", genre='" + genre + "'}";
    }
}