package com.balazsholczer.adapter;

public class ModernAdapterDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Adapter Pattern ===");
        AudioPlayer traditional = new AudioPlayer();
        traditional.play("mp3", "song.mp3");
        traditional.play("vlc", "movie.vlc");
        traditional.play("mp4", "video.mp4");
        traditional.play("avi", "clip.avi");
        
        System.out.println("\n=== Functional Adapter Pattern ===");
        FunctionalMediaAdapter.play("mp3", "song.mp3");
        FunctionalMediaAdapter.play("vlc", "movie.vlc");
        FunctionalMediaAdapter.play("mp4", "video.mp4");
        FunctionalMediaAdapter.play("avi", "clip.avi");
        
        System.out.println("\n=== Enum Adapter Pattern ===");
        EnumMediaAdapter.play("mp3", "song.mp3");
        EnumMediaAdapter.play("vlc", "movie.vlc");
        EnumMediaAdapter.play("mp4", "video.mp4");
        EnumMediaAdapter.play("avi", "clip.avi");
        
        System.out.println("\n=== Generic Adapter Pattern ===");
        GenericAdapter<String, Void> generic = GenericAdapter.createMediaAdapter();
        try {
            generic.adapt("mp3", "song.mp3");
            generic.adapt("vlc", "movie.vlc");
            generic.adapt("mp4", "video.mp4");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - Record-based:");
        var mediaFile = new FunctionalMediaAdapter.MediaFile("vlc", "presentation.vlc");
        FunctionalMediaAdapter.play(mediaFile);
        
        System.out.println("Enum - All Supported Formats:");
        for (EnumMediaAdapter adapter : EnumMediaAdapter.values()) {
            System.out.println("Supported format: " + adapter.name());
        }
        
        System.out.println("Generic - Type-safe adaptation:");
        try {
            generic.adapt("unknown", "file.unknown");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}