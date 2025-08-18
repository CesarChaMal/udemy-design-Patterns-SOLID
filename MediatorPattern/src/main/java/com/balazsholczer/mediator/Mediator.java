package com.balazsholczer.mediator;

public interface Mediator {
    void sendMessage(String message, Colleague colleague);
}