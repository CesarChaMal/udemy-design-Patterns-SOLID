package com.balazsholczer.streamcollector;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class StreamCollectorTest {
    
    @Test
    public void testCustomCollector() {
        List<String> words = List.of("Java", "Stream", "Collector");
        String result = words.stream()
            .collect(App.joining(", ", "[", "]"));
            
        assertEquals("[Java, Stream, Collector]", result);
    }
    
    @Test
    public void testEmptyStream() {
        List<String> empty = List.of();
        String result = empty.stream()
            .collect(App.joining(", ", "[", "]"));
            
        assertEquals("[]", result);
    }
    
    @Test
    public void testSingleElement() {
        List<String> single = List.of("Java");
        String result = single.stream()
            .collect(App.joining(", ", "[", "]"));
            
        assertEquals("[Java]", result);
    }
}