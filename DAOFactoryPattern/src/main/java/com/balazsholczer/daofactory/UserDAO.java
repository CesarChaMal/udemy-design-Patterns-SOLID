package com.balazsholczer.daofactory;

import java.util.List;

public interface UserDAO {
    void save(User user);
    User findById(String id);
    List<User> findAll();
    void delete(String id);
}