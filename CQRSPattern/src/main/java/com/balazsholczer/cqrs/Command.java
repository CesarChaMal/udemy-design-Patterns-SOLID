package com.balazsholczer.cqrs;

public interface Command {
    String getCommandType();
}