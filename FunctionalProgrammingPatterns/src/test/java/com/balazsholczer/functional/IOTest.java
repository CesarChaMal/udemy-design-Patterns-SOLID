package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IOTest {

    @Test
    void testIOBasic() {
        IO<String> io = IO.delay(() -> "Hello World");
        assertEquals("Hello World", io.unsafeRun());
    }

    @Test
    void testIOPure() {
        IO<String> io = IO.pure("Hello World");
        assertEquals("Hello World", io.unsafeRun());
    }

    @Test
    void testIOMap() {
        IO<String> io = IO.delay(() -> "Hello");
        IO<Integer> mapped = io.map(String::length);
        assertEquals(5, mapped.unsafeRun());
    }

    @Test
    void testIOFlatMap() {
        IO<String> io = IO.delay(() -> "Hello");
        IO<String> flatMapped = io.flatMap(s -> IO.delay(() -> s + " World"));
        assertEquals("Hello World", flatMapped.unsafeRun());
    }

    @Test
    void testIOSequence() {
        IO<Integer> io1 = IO.delay(() -> 5);
        IO<Integer> io2 = IO.delay(() -> 3);
        IO<Integer> combined = io1.flatMap(a -> io2.map(b -> a + b));
        assertEquals(8, combined.unsafeRun());
    }
    
    @Test
    void testIOPrintln() {
        IO<Void> printIO = IO.println("Test message");
        assertDoesNotThrow(() -> printIO.unsafeRun());
    }
    
    @Test
    void testIOComposition() {
        IO<String> greeting = IO.pure("Hello")
            .map(s -> s + ", World!")
            .flatMap(s -> IO.delay(() -> s.toUpperCase()));
        
        assertEquals("HELLO, WORLD!", greeting.unsafeRun());
    }

    @Test
    void testIOLazyEvaluation() {
        boolean[] executed = {false};
        IO<String> lazy = IO.delay(() -> {
            executed[0] = true;
            return "Executed";
        });
        
        assertFalse(executed[0]); // Should not execute until run
        assertEquals("Executed", lazy.unsafeRun());
        assertTrue(executed[0]); // Now should be executed
    }

    @Test
    void testIOChaining() {
        IO<Integer> result = IO.pure(5)
            .flatMap(x -> IO.delay(() -> x * 2))
            .flatMap(x -> IO.pure(x + 1))
            .map(x -> x * 3);
        
        assertEquals(33, result.unsafeRun()); // ((5*2)+1)*3 = 33
    }

    @Test
    void testIOErrorHandling() {
        IO<String> errorIO = IO.delay(() -> {
            throw new RuntimeException("Test error");
        });
        
        assertThrows(RuntimeException.class, () -> errorIO.unsafeRun());
    }
}