package com.balazsholczer.singleton;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Singleton Pattern
 * Tests Enum, Lazy, Functional, Atomic, ReentrantLock, and Record approaches
 */
class SingletonTest {
    
    @Test
    void testEnumSingleton() {
        SingletonClass instance1 = SingletonClass.INSTANCE;
        SingletonClass instance2 = SingletonClass.INSTANCE;
        
        assertSame(instance1, instance2);
        
        instance1.setCounter(10);
        assertEquals(10, instance2.getCounter());
        
        instance2.setCounter(20);
        assertEquals(20, instance1.getCounter());
    }
    
    @Test
    void testLazySingleton() {
        LazySingleton instance1 = LazySingleton.getInstance();
        LazySingleton instance2 = LazySingleton.getInstance();
        
        assertSame(instance1, instance2);
        
        instance1.setCounter(15);
        assertEquals(15, instance2.getCounter());
        
        instance2.setCounter(25);
        assertEquals(25, instance1.getCounter());
    }
    
    @Test
    void testFunctionalSingleton() {
        FunctionalSingleton instance1 = FunctionalSingleton.getInstance();
        FunctionalSingleton instance2 = FunctionalSingleton.getInstance();
        
        assertSame(instance1, instance2);
        
        instance1.setCounter(30);
        assertEquals(30, instance2.getCounter());
        
        instance2.setCounter(40);
        assertEquals(40, instance1.getCounter());
    }
    
    @Test
    void testAtomicSingleton() {
        AtomicSingleton instance1 = AtomicSingleton.getInstance();
        AtomicSingleton instance2 = AtomicSingleton.getInstance();
        
        assertSame(instance1, instance2);
        
        instance1.setCounter(50);
        assertEquals(50, instance2.getCounter());
    }
    
    @Test
    void testReentrantLockSingleton() {
        ReentrantLockSingleton instance1 = ReentrantLockSingleton.getInstance();
        ReentrantLockSingleton instance2 = ReentrantLockSingleton.getInstance();
        
        assertSame(instance1, instance2);
        
        instance1.setCounter(60);
        assertEquals(60, instance2.getCounter());
    }
    
    @Test
    void testRecordSingleton() {
        RecordSingleton.SingletonData instance1 = RecordSingleton.getInstance();
        RecordSingleton.SingletonData instance2 = RecordSingleton.getInstance();
        
        assertSame(instance1, instance2);
        
        RecordSingleton.updateInstance(70);
        assertEquals(70, RecordSingleton.getCounter());
        assertEquals(70, RecordSingleton.getInstance().counter());
    }
    
    @Test
    void testSingletonBehavior() {
        // Test that all singleton implementations maintain single instance
        
        // Enum singleton
        SingletonClass enum1 = SingletonClass.INSTANCE;
        SingletonClass enum2 = SingletonClass.INSTANCE;
        assertSame(enum1, enum2);
        
        // Lazy singleton
        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();
        assertSame(lazy1, lazy2);
        
        // Functional singleton
        FunctionalSingleton func1 = FunctionalSingleton.getInstance();
        FunctionalSingleton func2 = FunctionalSingleton.getInstance();
        assertSame(func1, func2);
    }
    
    @Test
    void testStateSharing() {
        // Test that state is shared across all references
        
        SingletonClass enumInstance = SingletonClass.INSTANCE;
        enumInstance.setCounter(100);
        assertEquals(100, SingletonClass.INSTANCE.getCounter());
        
        LazySingleton lazyInstance = LazySingleton.getInstance();
        lazyInstance.setCounter(200);
        assertEquals(200, LazySingleton.getInstance().getCounter());
        
        FunctionalSingleton funcInstance = FunctionalSingleton.getInstance();
        funcInstance.setCounter(300);
        assertEquals(300, FunctionalSingleton.getInstance().getCounter());
    }
    
    @Test
    void testMultipleAccess() {
        // Test multiple consecutive accesses return same instance
        
        SingletonClass[] enumInstances = new SingletonClass[5];
        for (int i = 0; i < 5; i++) {
            enumInstances[i] = SingletonClass.INSTANCE;
        }
        
        for (int i = 1; i < 5; i++) {
            assertSame(enumInstances[0], enumInstances[i]);
        }
        
        LazySingleton[] lazyInstances = new LazySingleton[5];
        for (int i = 0; i < 5; i++) {
            lazyInstances[i] = LazySingleton.getInstance();
        }
        
        for (int i = 1; i < 5; i++) {
            assertSame(lazyInstances[0], lazyInstances[i]);
        }
    }
    
    @Test
    void testCounterFunctionality() {
        // Test that counter works correctly in all implementations
        
        SingletonClass enumSingleton = SingletonClass.INSTANCE;
        enumSingleton.setCounter(0);
        assertEquals(0, enumSingleton.getCounter());
        
        LazySingleton lazySingleton = LazySingleton.getInstance();
        lazySingleton.setCounter(0);
        assertEquals(0, lazySingleton.getCounter());
        
        FunctionalSingleton funcSingleton = FunctionalSingleton.getInstance();
        funcSingleton.setCounter(0);
        assertEquals(0, funcSingleton.getCounter());
    }
    
    @Test
    void testEquivalence() {
        // All singleton approaches should maintain single instance behavior
        
        // Reset all counters
        SingletonClass.INSTANCE.setCounter(1);
        LazySingleton.getInstance().setCounter(1);
        FunctionalSingleton.getInstance().setCounter(1);
        
        // Test that getting instance multiple times returns same object
        assertTrue(SingletonClass.INSTANCE == SingletonClass.INSTANCE);
        assertTrue(LazySingleton.getInstance() == LazySingleton.getInstance());
        assertTrue(FunctionalSingleton.getInstance() == FunctionalSingleton.getInstance());
        
        // Test that state persists across calls
        SingletonClass.INSTANCE.setCounter(42);
        assertEquals(42, SingletonClass.INSTANCE.getCounter());
        
        LazySingleton.getInstance().setCounter(42);
        assertEquals(42, LazySingleton.getInstance().getCounter());
        
        FunctionalSingleton.getInstance().setCounter(42);
        assertEquals(42, FunctionalSingleton.getInstance().getCounter());
    }
    
    @Test
    void testSingletonIdentity() {
        // Test object identity (not just equality)
        
        SingletonClass enum1 = SingletonClass.INSTANCE;
        SingletonClass enum2 = SingletonClass.INSTANCE;
        assertTrue(enum1 == enum2);
        assertEquals(System.identityHashCode(enum1), System.identityHashCode(enum2));
        
        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();
        assertTrue(lazy1 == lazy2);
        assertEquals(System.identityHashCode(lazy1), System.identityHashCode(lazy2));
        
        FunctionalSingleton func1 = FunctionalSingleton.getInstance();
        FunctionalSingleton func2 = FunctionalSingleton.getInstance();
        assertTrue(func1 == func2);
        assertEquals(System.identityHashCode(func1), System.identityHashCode(func2));
    }
}