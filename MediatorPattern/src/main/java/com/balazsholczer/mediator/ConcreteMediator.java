package com.balazsholczer.mediator;

import java.util.ArrayList;
import java.util.List;

public class ConcreteMediator implements Mediator {
    
    private final List<Colleague> colleagues = new ArrayList<>();
    
    public void addColleague(Colleague colleague) {
        colleagues.add(colleague);
    }
    
    @Override
    public void sendMessage(String message, Colleague sender) {
        for (Colleague colleague : colleagues) {
            if (colleague != sender) {
                colleague.receive(message);
            }
        }
    }
}