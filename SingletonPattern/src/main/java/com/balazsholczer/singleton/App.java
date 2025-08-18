package com.balazsholczer.singleton;

public class App {

	public static void main(String[] args) {
		System.out.println("=== Enum Singleton (Eager Initialization) ===");
		SingletonClass.INSTANCE.setCounter(10);
		System.out.println("Counter: " + SingletonClass.INSTANCE.getCounter());
		
		System.out.println("\n=== Lazy Singleton (Thread-Safe) ===");
		LazySingleton lazy = LazySingleton.getInstance();
		lazy.setCounter(20);
		System.out.println("Counter: " + lazy.getCounter());
		
		// Verify same instance
		LazySingleton lazy2 = LazySingleton.getInstance();
		System.out.println("Same instance: " + (lazy == lazy2));
		System.out.println("Counter from second reference: " + lazy2.getCounter());
	}
}
