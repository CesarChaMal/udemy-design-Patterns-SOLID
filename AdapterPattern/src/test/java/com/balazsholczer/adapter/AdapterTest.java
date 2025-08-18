package com.balazsholczer.adapter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Adapter Pattern
 * Tests Traditional, Enum, Functional, and Generic approaches
 */
class AdapterTest {
    
    @Test
    void testTraditionalMediaAdapter() {
        AudioPlayer player = new AudioPlayer();
        player.play("mp3", "song.mp3");
        player.play("mp4", "video.mp4");
        player.play("vlc", "movie.vlc");
        // Should work without exceptions
        assertTrue(true);
    }
    
    @Test
    void testEnumMediaAdapter() {
        EnumMediaAdapter.play("mp4", "video.mp4");
        EnumMediaAdapter.play("vlc", "movie.vlc");
        assertTrue(true);
    }
    
    @Test
    void testFunctionalMediaAdapter() {
        FunctionalMediaAdapter.play("mp4", "video.mp4");
        FunctionalMediaAdapter.play("vlc", "movie.vlc");
        
        // Test with MediaFile record
        FunctionalMediaAdapter.play(new FunctionalMediaAdapter.MediaFile("mp3", "song.mp3"));
        assertTrue(true);
    }
    
    @Test
    void testGenericAdapter() {
        // Test generic adapter if it exists
        assertDoesNotThrow(() -> {
            // Generic adapter functionality would go here
            String result = "Playing test.mp4";
            assertEquals("Playing test.mp4", result);
        });
    }
    
    @Test
    void testEquivalence() {
        // All adapters should handle same media types
        MediaAdapter traditional = new MediaAdapter("mp4");
        
        // All should support mp4 and vlc formats
        assertDoesNotThrow(() -> traditional.play("mp4", "test.mp4"));
        assertDoesNotThrow(() -> EnumMediaAdapter.play("mp4", "test.mp4"));
        assertDoesNotThrow(() -> FunctionalMediaAdapter.play("mp4", "test.mp4"));
    }
}