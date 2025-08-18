package com.balazsholczer.unitofwork;

public abstract class Entity {
    protected String id;
    protected boolean dirty = false;
    
    public Entity(String id) {
        this.id = id;
    }
    
    public String getId() { return id; }
    public boolean isDirty() { return dirty; }
    public void markDirty() { this.dirty = true; }
    public void markClean() { this.dirty = false; }
}