package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EitherTest {

    @Test
    void testRight() {
        Either<String, Integer> right = Either.right(42);
        assertTrue(right.isRight());
        assertFalse(right.isLeft());
        assertEquals(42, right.getRight());
    }

    @Test
    void testLeft() {
        Either<String, Integer> left = Either.left("Error");
        assertTrue(left.isLeft());
        assertFalse(left.isRight());
        assertEquals("Error", left.getLeft());
    }

    @Test
    void testMapRight() {
        Either<String, Integer> right = Either.right(5);
        Either<String, Integer> mapped = right.map(x -> x * 2);
        assertTrue(mapped.isRight());
        assertEquals(10, mapped.getRight());
    }

    @Test
    void testMapLeft() {
        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> mapped = left.map(x -> x * 2);
        assertTrue(mapped.isLeft());
        assertEquals("Error", mapped.getLeft());
    }

    @Test
    void testFlatMapLeft() {
        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> flatMapped = left.flatMap(x -> Either.right(x * 2));
        assertTrue(flatMapped.isLeft());
        assertEquals("Error", flatMapped.getLeft());
    }

    @Test
    void testMapLeftTransform() {
        Either<String, Integer> left = Either.left("error");
        Either<String, Integer> mapped = left.mapLeft(String::toUpperCase);
        assertTrue(mapped.isLeft());
        assertEquals("ERROR", mapped.getLeft());
    }

    @Test
    void testFlatMap() {
        Either<String, Integer> right = Either.right(5);
        Either<String, Integer> flatMapped =
                right.flatMap(x -> Either.right(x * 2));
        assertTrue(flatMapped.isRight());
        assertEquals(10, flatMapped.getRight());
    }

    @Test
    void testChaining() {
        // Seed the chain with an explicit left type so L = String (not Object)
        Either<String, Integer> result = Either.<String, Integer>right(5)
                .map(x -> x * 2)
                .flatMap(x -> Either.right(x + 1))
                .map(x -> x * 3);

        assertTrue(result.isRight());
        assertEquals(33, result.getRight());
    }

    @Test
    void testErrorPropagation() {
        Either<String, Integer> right = Either.right(5);
        Either<String, Integer> mapped = right.map(x -> x * 2);
        Either<String, Integer> error = Either.left("Error occurred");
        Either<String, Integer> result = error.map(x -> x * 3);

        assertTrue(result.isLeft());
        assertEquals("Error occurred", result.getLeft());
    }
}
