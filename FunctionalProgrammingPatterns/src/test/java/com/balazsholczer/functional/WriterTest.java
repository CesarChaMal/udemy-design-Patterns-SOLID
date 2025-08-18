package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;

class WriterTest {

    @Test
    void testWriterBasic() {
        Writer<String, Integer> writer = new Writer<>(42, "Initial log");
        assertEquals(42, writer.getValue());
        assertEquals("Initial log", writer.getLog());
    }

    @Test
    void testWriterTell() {
        Writer<String, Integer> writer = Writer.tell(42, "Initial log");
        assertEquals(42, writer.getValue());
        assertEquals("Initial log", writer.getLog());
    }

    @Test
    void testWriterPure() {
        Writer<String, Integer> writer = Writer.pure(42, "");
        assertEquals(42, writer.getValue());
        assertEquals("", writer.getLog());
    }

    @Test
    void testWriterMap() {
        Writer<String, Integer> writer = new Writer<>(5, "Number: ");
        Writer<String, Integer> mapped = writer.map(x -> x * 2);
        assertEquals(10, mapped.getValue());
        assertEquals("Number: ", mapped.getLog());
    }

    @Test
    void testWriterFlatMap() {
        Writer<String, Integer> writer = new Writer<>(5, "Start: ");
        Function<String, Function<String, String>> combiner = log1 -> log2 -> log1 + log2;
        Writer<String, Integer> flatMapped = writer.flatMap(x -> 
            new Writer<>(x * 2, "Doubled: "), combiner);
        assertEquals(10, flatMapped.getValue());
        assertEquals("Start: Doubled: ", flatMapped.getLog());
    }

    @Test
    void testWriterSequence() {
        Writer<String, Integer> w1 = new Writer<>(5, "First ");
        Writer<String, Integer> w2 = new Writer<>(3, "Second ");
        Function<String, Function<String, String>> combiner = log1 -> log2 -> log1 + log2;
        Writer<String, Integer> combined = w1.flatMap(a -> 
            w2.map(b -> a + b), combiner);
        assertEquals(8, combined.getValue());
        assertEquals("First Second ", combined.getLog());
    }
    
    @Test
    void testWriterChaining() {
        Function<String, Function<String, String>> stringCombiner = log1 -> log2 -> log1 + log2;
        
        Writer<String, Integer> result = Writer.tell(10, "Started with 10; ")
            .flatMap(x -> Writer.tell(x + 5, "Added 5; "), stringCombiner)
            .flatMap(x -> Writer.tell(x * 2, "Doubled; "), stringCombiner);
        
        assertEquals(30, result.getValue());
        assertEquals("Started with 10; Added 5; Doubled; ", result.getLog());
    }

    @Test
    void testWriterMonadLaws() {
        Function<String, Function<String, String>> combiner = log1 -> log2 -> log1 + log2;
        
        // Left identity: pure(a).flatMap(f) = f(a)
        Writer<String, Integer> leftId1 = Writer.pure(5, "").flatMap(x -> Writer.tell(x * 2, "doubled"), combiner);
        Writer<String, Integer> leftId2 = Writer.tell(10, "doubled");
        assertEquals(leftId1.getValue(), leftId2.getValue());
        
        // Right identity: m.flatMap(pure) = m
        Writer<String, Integer> m = Writer.tell(42, "test");
        Writer<String, Integer> rightId = m.flatMap(x -> Writer.pure(x, ""), combiner);
        assertEquals(m.getValue(), rightId.getValue());
    }

    @Test
    void testWriterLogging() {
        Function<String, Function<String, String>> combiner = log1 -> log2 -> log1 + log2;
        
        Writer<String, Integer> computation = Writer.pure(1, "")
            .flatMap(x -> Writer.tell(x + 1, "incremented to 2; "), combiner)
            .flatMap(x -> Writer.tell(x * 3, "multiplied by 3; "), combiner)
            .flatMap(x -> Writer.tell(x - 1, "decremented by 1; "), combiner);
        
        assertEquals(5, computation.getValue()); // ((1+1)*3)-1 = 5
        assertEquals("incremented to 2; multiplied by 3; decremented by 1; ", computation.getLog());
    }
}