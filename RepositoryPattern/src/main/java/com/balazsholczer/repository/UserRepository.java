package com.balazsholczer.repository;

import java.util.List;

public interface UserRepository extends Repository<User, String> {
    List<User> findByName(String name);
    List<User> findByEmail(String email);
}