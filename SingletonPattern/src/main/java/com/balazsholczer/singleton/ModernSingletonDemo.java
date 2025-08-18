package com.balazsholczer.singleton;

public class ModernSingletonDemo {
    
    public static void main(String[] args) {
        System.out.println("\n=== ReentrantLock Singleton ===");
        ReentrantLockSingleton reentrant = ReentrantLockSingleton.getInstance();
        reentrant.setCounter(60);
        System.out.println("Counter: " + reentrant.getCounter());
        System.out.println("Same instance: " + (reentrant == ReentrantLockSingleton.getInstance()));

        System.out.println("\n=== Lazy Thread-Safe Singleton ===");
        LazySingleton lazy = LazySingleton.getInstance();
        lazy.setCounter(20);
        System.out.println("Counter: " + lazy.getCounter());
        System.out.println("Same instance: " + (lazy == LazySingleton.getInstance()));

        System.out.println("=== Traditional Enum Singleton ===");
        SingletonClass.INSTANCE.setCounter(10);
        System.out.println("Counter: " + SingletonClass.INSTANCE.getCounter());

        System.out.println("\n=== Functional Singleton ===");
        FunctionalSingleton functional = FunctionalSingleton.getInstance();
        functional.setCounter(30);
        System.out.println("Counter: " + functional.getCounter());
        System.out.println("Same instance: " + (functional == FunctionalSingleton.getInstance()));
        
        System.out.println("\n=== Record Singleton ===");
        RecordSingleton.updateInstance(40);
        System.out.println("Counter: " + RecordSingleton.getCounter());
        var data1 = RecordSingleton.getInstance();
        var data2 = RecordSingleton.getInstance();
        System.out.println("Same instance: " + (data1 == data2));
        
        System.out.println("\n=== Atomic Singleton ===");
        AtomicSingleton atomic = AtomicSingleton.getInstance();
        atomic.setCounter(50);
        System.out.println("Counter: " + atomic.getCounter());
        System.out.println("Increment: " + atomic.incrementAndGet());
        System.out.println("Same instance: " + (atomic == AtomicSingleton.getInstance()));
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Record - Immutable Updates:");
        var originalData = RecordSingleton.getInstance();
        RecordSingleton.updateInstance(100);
        var newData = RecordSingleton.getInstance();
        System.out.println("Original: " + originalData.counter() + ", New: " + newData.counter());
        
        System.out.println("Atomic - Thread-Safe Operations:");
        AtomicSingleton atomic2 = AtomicSingleton.getInstance();
        System.out.println("Atomic increment: " + atomic2.incrementAndGet());
        System.out.println("Current value: " + atomic2.getCounter());
    }
}