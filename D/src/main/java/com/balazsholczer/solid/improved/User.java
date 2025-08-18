package com.balazsholczer.solid.improved;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modern immutable User entity
 */
public final class User {
    private final String id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    
    public User(String id, String username, String email, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.username = Objects.requireNonNull(username, "Username cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Created date cannot be null");
    }
    
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("User{id='%s', username='%s', email='%s', createdAt=%s}", 
                           id, username, email, createdAt);
    }
}