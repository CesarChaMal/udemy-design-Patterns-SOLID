package com.balazsholczer.frontcontroller;

public interface Command {
    void execute(Request request, Response response);
}