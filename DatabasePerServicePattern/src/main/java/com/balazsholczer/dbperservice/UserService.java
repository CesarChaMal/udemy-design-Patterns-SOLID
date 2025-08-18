package com.balazsholczer.dbperservice;

public class UserService {
    private final UserDatabase database;
    
    public UserService() {
        this.database = new UserDatabase();
    }
    
    public void createUser(String userId, String name, String email) {
        System.out.println("UserService: Creating user " + userId);
        database.saveUser(userId, name, email);
    }
    
    public UserDatabase.User getUser(String userId) {
        System.out.println("UserService: Getting user " + userId);
        return database.findUser(userId);
    }
    
    public void deleteUser(String userId) {
        System.out.println("UserService: Deleting user " + userId);
        database.delete(userId);
    }
}