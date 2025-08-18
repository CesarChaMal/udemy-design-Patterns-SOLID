package com.balazsholczer.cqrs;

public interface Query<T> {
    String getQueryType();
}