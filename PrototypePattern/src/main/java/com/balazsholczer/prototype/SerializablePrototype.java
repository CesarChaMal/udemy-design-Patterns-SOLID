package com.balazsholczer.prototype;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializablePrototype {
    
    public static class SerializableBook implements Serializable, Cloneable {
        private static final long serialVersionUID = 1L;
        
        private String title;
        private String author;
        private int pages;
        private List<String> tags;
        
        public SerializableBook(String title, String author, int pages, List<String> tags) {
            this.title = title;
            this.author = author;
            this.pages = pages;
            this.tags = List.copyOf(tags);
        }
        
        // Deep clone using serialization
        public SerializableBook deepClone() {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                
                oos.writeObject(this);
                
                try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                     ObjectInputStream ois = new ObjectInputStream(bis)) {
                    
                    System.out.println("SerializablePrototype: Deep cloned via serialization");
                    return (SerializableBook) ois.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Deep clone failed", e);
            }
        }
        
        // Shallow clone using Object.clone()
        @Override
        public SerializableBook clone() {
            try {
                System.out.println("SerializablePrototype: Shallow cloned");
                return (SerializableBook) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Clone not supported", e);
            }
        }
        
        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        
        public int getPages() { return pages; }
        public void setPages(int pages) { this.pages = pages; }
        
        public List<String> getTags() { return List.copyOf(tags); }
        public void setTags(List<String> tags) { this.tags = List.copyOf(tags); }
        
        @Override
        public String toString() {
            return "SerializableBook{title='" + title + "', author='" + author + 
                   "', pages=" + pages + ", tags=" + tags + "}";
        }
    }
    
    public static class DeepCloneRegistry {
        private final Map<String, SerializableBook> prototypes = new ConcurrentHashMap<>();
        
        public void register(String key, SerializableBook prototype) {
            prototypes.put(key, prototype);
            System.out.println("DeepCloneRegistry: Registered " + key);
        }
        
        public SerializableBook getShallowClone(String key) {
            SerializableBook prototype = prototypes.get(key);
            if (prototype != null) {
                return prototype.clone();
            }
            throw new IllegalArgumentException("No prototype found: " + key);
        }
        
        public SerializableBook getDeepClone(String key) {
            SerializableBook prototype = prototypes.get(key);
            if (prototype != null) {
                return prototype.deepClone();
            }
            throw new IllegalArgumentException("No prototype found: " + key);
        }
        
        public void demonstrateCloneDifference(String key) {
            SerializableBook original = prototypes.get(key);
            if (original == null) return;
            
            SerializableBook shallow = original.clone();
            SerializableBook deep = original.deepClone();
            
            System.out.println("Original: " + original);
            System.out.println("Shallow clone: " + shallow);
            System.out.println("Deep clone: " + deep);
            System.out.println("Shallow == Original: " + (shallow == original));
            System.out.println("Deep == Original: " + (deep == original));
        }
    }
}