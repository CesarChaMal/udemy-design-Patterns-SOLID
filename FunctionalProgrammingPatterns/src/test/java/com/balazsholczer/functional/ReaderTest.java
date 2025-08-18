package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @Test
    void testReaderBasic() {
        Reader<String, Integer> reader = String::length;
        assertEquals(5, reader.run("Hello"));
    }

    @Test
    void testReaderMap() {
        Reader<String, Integer> reader = String::length;
        Reader<String, Integer> mapped = reader.map(len -> len * 2);
        assertEquals(10, mapped.run("Hello"));
    }

    @Test
    void testReaderFlatMap() {
        Reader<String, Integer> reader = String::length;
        Reader<String, String> flatMapped = reader.flatMap(len ->
                s -> s + " has " + len + " characters");
        assertEquals("Hello has 5 characters", flatMapped.run("Hello"));
    }

    @Test
    void testReaderComposition() {
        Reader<String, String> upper = String::toUpperCase;
        Reader<String, Integer> length = String::length;
        Reader<String, String> combined = upper.flatMap(u ->
                length.map(l -> u + " (" + l + ")"));
        assertEquals("HELLO (5)", combined.run("Hello"));
    }

    @Test
    void testReaderPure() {
        Reader<String, Integer> pureReader = Reader.pure(42);
        assertEquals(42, pureReader.run("any string"));
    }

    @Test
    void testReaderAsk() {
        Reader<String, String> askReader = Reader.ask();
        assertEquals("Hello", askReader.run("Hello"));
    }

    @Test
    void testReaderLocal() {
        Reader<String, Integer> lengthReader = String::length;
        Reader<String, Integer> upperLengthReader = Reader.local(
                String::toUpperCase,
                lengthReader
        );

        assertEquals(5, upperLengthReader.run("hello"));
        assertEquals(5, lengthReader.run("HELLO"));
    }

    @Test
    void testReaderDependencyInjection() {
        record Config(String prefix, int multiplier) {}

        Reader<Config, String> greetingReader = config ->
                config.prefix() + " World!";

        Reader<Config, Integer> lengthReader = greetingReader.map(String::length);

        Reader<Config, Integer> multipliedReader = lengthReader.flatMap(len ->
                config -> len * config.multiplier());

        Config config = new Config("Hello", 3);
        assertEquals(36, multipliedReader.run(config));
    }

    @Test
    void testReaderMonadLaws() {
        // Left identity: pure(a).flatMap(f) = f(a)
        Reader<String, Integer> f = s -> s.length() * 2;

        // Force R=String at the pure() call (otherwise R becomes Object)
        Reader<String, Integer> leftId1 =
                Reader.<String, Integer>pure(5).flatMap(x -> s -> f.run(s) + x);

        Reader<String, Integer> leftId2 = s -> f.run(s) + 5;
        assertEquals(leftId1.run("test"), leftId2.run("test"));

        // Right identity: m.flatMap(pure) = m
        Reader<String, Integer> m = String::length;
        Reader<String, Integer> rightId = m.flatMap(Reader::pure);
        assertEquals(m.run("test"), rightId.run("test"));
    }

    @Test
    void testReaderEnvironmentPassing() {
        record Database(java.util.Map<String, String> data) {}

        Reader<Database, String> getUserName = db ->
                db.data().getOrDefault("user", "unknown");

        Reader<Database, String> getGreeting = getUserName.map(name ->
                "Hello, " + name + "!");

        Database db = new Database(java.util.Map.of("user", "Alice"));
        assertEquals("Hello, Alice!", getGreeting.run(db));

        Database emptyDb = new Database(java.util.Map.of());
        assertEquals("Hello, unknown!", getGreeting.run(emptyDb));
    }

    @Test
    void testReaderComputationChain() {
        Reader<Integer, Integer> addTen = x -> x + 10;
        Reader<Integer, Integer> multiplyByTwo = x -> x * 2;

        Reader<Integer, String> chain = addTen
                .flatMap(a -> multiplyByTwo.map(b -> a + b))
                .map(result -> "Result: " + result + " (computed)");

        assertEquals("Result: 25 (computed)", chain.run(5)); // (5+10) + (5*2) = 25
    }
}
