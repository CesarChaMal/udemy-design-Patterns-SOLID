package com.balazsholczer.virtualthread;

import org.junit.jupiter.api.Test;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

public class VirtualThreadTest {
    
    @Test
    public void testVirtualThreadExecution() {
        AtomicInteger counter = new AtomicInteger(0);
        
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100)
                .forEach(i -> executor.submit(counter::incrementAndGet));
        }
        
        assertEquals(100, counter.get());
    }
    
    @Test
    public void testVirtualThreadCreation() {
        Thread virtualThread = Thread.ofVirtual()
            .name("test-virtual-thread")
            .start(() -> {});
            
        assertTrue(virtualThread.isVirtual());
    }
}