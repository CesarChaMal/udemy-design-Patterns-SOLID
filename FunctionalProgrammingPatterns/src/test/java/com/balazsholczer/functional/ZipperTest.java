package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZipperTest {

    @Test
    void testZipperBasic() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        Zipper<Integer> zipper = Zipper.fromList(list);
        
        assertEquals(1, zipper.focus());
        assertTrue(zipper.isAtStart()); // At start when focused on first element
        assertFalse(zipper.isAtEnd());
    }

    @Test
    void testZipperNavigation() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        Zipper<Integer> zipper = Zipper.fromList(list);
        
        Zipper<Integer> moved = zipper.moveRight().moveRight();
        assertEquals(3, moved.focus());
        
        Zipper<Integer> back = moved.moveLeft();
        assertEquals(2, back.focus());
    }

    @Test
    void testZipperModify() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        Zipper<Integer> zipper = Zipper.fromList(list);
        
        Zipper<Integer> modified = zipper.moveRight().update(x -> x * 10);
        assertEquals(20, modified.focus());
        
        ImmutableList<Integer> result = modified.toList();
        assertEquals(ImmutableList.of(1, 20, 3, 4, 5), result);
    }

    @Test
    void testZipperInsert() {
        ImmutableList<Integer> list = ImmutableList.of(1, 3, 5);
        Zipper<Integer> zipper = Zipper.fromList(list);
        
        Zipper<Integer> inserted = zipper.moveRight().insertLeft(2);
        ImmutableList<Integer> result = inserted.toList();
        assertEquals(ImmutableList.of(1, 2, 3, 5), result);
    }
}