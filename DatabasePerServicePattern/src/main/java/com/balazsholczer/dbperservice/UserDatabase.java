package com.balazsholczer.dbperservice;

public class UserDatabase extends Database {
    
    public UserDatabase() {
        super("User");
    }
    
    public void saveUser(String userId, String name, String email) {
        User user = new User(userId, name, email);
        save(userId, user);
    }
    
    public User findUser(String userId) {
        return (User) find(userId);
    }
    
    public static class User {
        private final String id;
        private final String name;
        private final String email;
        
        public User(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        
        @Override
        public String toString() {
            return "User{id='" + id + "', name='" + name + "', email='" + email + "'}";
        }
    }
}