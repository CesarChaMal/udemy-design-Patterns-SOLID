package com.balazsholczer.solid.improved;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Modern user service with dependency injection
 */
public class UserService {
    private final DatabaseRepository<User> repository;
    
    public UserService(DatabaseRepository<User> repository) {
        this.repository = Objects.requireNonNull(repository, "Repository cannot be null");
    }
    
    public CompletableFuture<User> createUser(String username, String email) {
        return CompletableFuture.supplyAsync(() -> {
            String id = generateId();
            User user = new User(id, username, email, LocalDateTime.now());
            System.out.println("UserService: Creating user with " + repository.getRepositoryType());
            return user;
        }).thenCompose(user -> 
            repository.save(user.getId(), user).thenApply(v -> user)
        );
    }
    
    public CompletableFuture<Optional<User>> getUser(String id) {
        System.out.println("UserService: Getting user from " + repository.getRepositoryType());
        return repository.findById(id);
    }
    
    public CompletableFuture<Boolean> deleteUser(String id) {
        System.out.println("UserService: Deleting user from " + repository.getRepositoryType());
        return repository.delete(id);
    }
    
    public CompletableFuture<Boolean> userExists(String id) {
        System.out.println("UserService: Checking user existence in " + repository.getRepositoryType());
        return repository.exists(id);
    }
    
    private String generateId() {
        return "USER-" + System.currentTimeMillis();
    }
}