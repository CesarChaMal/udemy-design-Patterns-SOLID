package com.balazsholczer.unitofwork;

import java.util.*;

public class UnitOfWork {
    
    private final Set<Entity> newEntities = new HashSet<>();
    private final Set<Entity> dirtyEntities = new HashSet<>();
    private final Set<Entity> removedEntities = new HashSet<>();
    
    public void registerNew(Entity entity) {
        if (removedEntities.contains(entity)) {
            removedEntities.remove(entity);
        } else {
            newEntities.add(entity);
        }
        System.out.println("UnitOfWork: Registered new entity " + entity.getId());
    }
    
    public void registerDirty(Entity entity) {
        if (!newEntities.contains(entity) && !removedEntities.contains(entity)) {
            dirtyEntities.add(entity);
        }
        System.out.println("UnitOfWork: Registered dirty entity " + entity.getId());
    }
    
    public void registerRemoved(Entity entity) {
        if (newEntities.contains(entity)) {
            newEntities.remove(entity);
        } else {
            dirtyEntities.remove(entity);
            removedEntities.add(entity);
        }
        System.out.println("UnitOfWork: Registered removed entity " + entity.getId());
    }
    
    public void commit() {
        System.out.println("UnitOfWork: Committing transaction...");
        
        // Insert new entities
        for (Entity entity : newEntities) {
            System.out.println("UnitOfWork: Inserting " + entity);
            entity.markClean();
        }
        
        // Update dirty entities
        for (Entity entity : dirtyEntities) {
            System.out.println("UnitOfWork: Updating " + entity);
            entity.markClean();
        }
        
        // Delete removed entities
        for (Entity entity : removedEntities) {
            System.out.println("UnitOfWork: Deleting " + entity);
        }
        
        // Clear all collections
        newEntities.clear();
        dirtyEntities.clear();
        removedEntities.clear();
        
        System.out.println("UnitOfWork: Transaction committed successfully");
    }
    
    public void rollback() {
        System.out.println("UnitOfWork: Rolling back transaction...");
        newEntities.clear();
        dirtyEntities.clear();
        removedEntities.clear();
        System.out.println("UnitOfWork: Transaction rolled back");
    }
    
    public boolean hasChanges() {
        return !newEntities.isEmpty() || !dirtyEntities.isEmpty() || !removedEntities.isEmpty();
    }
}