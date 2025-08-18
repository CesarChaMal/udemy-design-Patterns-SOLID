package com.balazsholczer.dto;

import java.time.LocalDateTime;

public class User {
    
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private boolean active;
    
    public User() {}
    
    public User(Long id, String username, String email, String password, 
                LocalDateTime createdAt, LocalDateTime lastLogin, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.active = active;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "', active=" + active + "}";
    }
}