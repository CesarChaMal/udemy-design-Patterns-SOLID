package com.balazsholczer.functional;

/**
 * Extended demonstrations for additional functional patterns
 */
public class AppExtended {
    
    public static void demonstrateEitherPattern() {
        System.out.println("\n--- Either Pattern (Error Handling) ---");
        
        Either<String, Integer> success = Either.right(42);
        Either<String, Integer> failure = Either.left("Error occurred");
        
        Either<String, Integer> doubled = success.map(x -> x * 2);
        Either<String, Integer> failedDouble = failure.map(x -> x * 2);
        
        System.out.println("Success: " + doubled);
        System.out.println("Failure: " + failedDouble);
    }
    
    public static void demonstrateTrampolinePattern() {
        System.out.println("\n--- Trampoline Pattern (Tail Recursion) ---");
        
        // Safe factorial using trampoline
        Trampoline<Long> factorial = factorialTrampoline(10, 1);
        long result = factorial.run();
        
        System.out.println("Factorial(10) = " + result);
    }
    
    private static Trampoline<Long> factorialTrampoline(long n, long acc) {
        if (n <= 1) {
            return Trampoline.done(acc);
        }
        return Trampoline.more(() -> factorialTrampoline(n - 1, n * acc));
    }

    public static void demonstrateStreamPattern() {
        System.out.println("\n--- Stream Pattern (Infinite Sequences) ---");

        Stream<Integer> naturals = Stream.from(1);

        // take returns ImmutableList
        ImmutableList<Integer> evens = naturals
                .filter(x -> x % 2 == 0)
                .take(5);

        System.out.println("First 5 even numbers: " + evens);
    }
    
    private static void printStream(Stream<Integer> stream) {
        if (!stream.isEmpty()) {
            System.out.print(stream.head() + " ");
            printStream(stream.tail());
        } else {
            System.out.println();
        }
    }
    
    public static void demonstrateContinuationPattern() {
        System.out.println("\n--- Continuation Pattern (CPS) ---");
        
        int result = Continuation.factorialCPS(5);
        
        System.out.println("Factorial(5) using CPS: " + result);
        
        // Demonstrate continuation composition
        Continuation<Integer, String> toString = Object::toString;
        Continuation<Integer, String> prefixed = toString.map(s -> "Result: " + s);
        
        System.out.println("Continuation composition: " + prefixed.apply(42));
    }
}