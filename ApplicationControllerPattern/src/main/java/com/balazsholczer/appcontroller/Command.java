package com.balazsholczer.appcontroller;

import java.util.Map;

public interface Command {
    String execute(Map<String, Object> context);
}