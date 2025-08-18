package com.balazsholczer.adapter;

/**
 * Adapter Pattern: allows incompatible interfaces to work together
 * 
 * Key Concepts:
 * - Converts interface of a class into another interface clients expect
 * - Lets classes work together that couldn't otherwise due to incompatible interfaces
 * - Acts as a bridge between two incompatible interfaces
 * 
 * Types:
 * - Object Adapter: uses composition (our example)
 * - Class Adapter: uses inheritance (multiple inheritance needed)
 * 
 * Benefits:
 * - Integrates third-party libraries without modifying their code
 * - Reuses existing functionality with different interfaces
 * - Separates interface conversion from business logic
 * - Follows Open/Closed Principle
 * 
 * Use Cases:
 * - Legacy system integration
 * - Third-party library integration
 * - API compatibility layers
 * - Data format conversion
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Adapter Pattern ===");
        
        AudioPlayer player = new AudioPlayer();
        
        // Native support
        player.play("mp3", "song.mp3");
        
        // Adapted third-party formats
        player.play("vlc", "movie.vlc");
        player.play("mp4", "video.mp4");
        
        // Unsupported format
        player.play("avi", "clip.avi");
        
        System.out.println("\n=== Run ModernAdapterDemo for all approaches ===");
    }
}