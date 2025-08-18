package com.balazsholczer.memento;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    
    private final List<Memento> mementoList = new ArrayList<>();
    
    public void add(Memento memento) {
        mementoList.add(memento);
        System.out.println("Caretaker: Memento saved (total: " + mementoList.size() + ")");
    }
    
    public Memento get(int index) {
        if (index >= 0 && index < mementoList.size()) {
            return mementoList.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid memento index: " + index);
    }
    
    public Memento getLatest() {
        if (mementoList.isEmpty()) {
            throw new IllegalStateException("No mementos available");
        }
        return mementoList.get(mementoList.size() - 1);
    }
    
    public int size() {
        return mementoList.size();
    }
    
    public void showHistory() {
        System.out.println("Caretaker: Memento history (" + mementoList.size() + " items):");
        for (int i = 0; i < mementoList.size(); i++) {
            System.out.println("  " + i + ": " + mementoList.get(i));
        }
    }
}