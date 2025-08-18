package com.balazsholczer.functional;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Complete Functional Programming Patterns Demonstration
 * Based on Category Theory and Lambda Calculus Foundations
 * 
 * All 24 Core Functional Patterns:
 * 
 * CATEGORY THEORY PATTERNS:
 * 
 * MONADS (7) - Computational contexts with composition:
 * - Maybe Monad (null safety)
 * - Either Monad (error handling)
 * - IO Monad (side effect management)
 * - State Monad (stateful computations)
 * - Reader Monad (dependency injection)
 * - Writer Monad (logging/accumulation)
 * - Free Monad (DSL construction)
 * 
 * FUNCTORS (3) - Structure-preserving mappings:
 * - Functor (mappable containers)
 * - Applicative Functor (multi-argument functions)
 * - Comonad (context extraction)
 * 
 * MORPHISMS (2) - Structure transformations:
 * - Fold/Catamorphism (data structure deconstruction)
 * - Unfold/Anamorphism (data structure construction)
 * 
 * ALGEBRAIC STRUCTURES (2) - Mathematical abstractions:
 * - Monoid (associative operations with identity)
 * - Semigroup (associative operations without identity)
 * 
 * ARROWS (2) - Generalized function composition:
 * - Kleisli Arrow (monadic function composition)
 * - Profunctor (contravariant/covariant mapping)
 * 
 * LAMBDA CALCULUS PATTERNS:
 * 
 * CORE PATTERNS (9) - Lambda calculus foundations:
 * - Higher-Order Functions (compose, curry, memoize)
 * - Immutable Data Structures (persistent collections)
 * - Lazy Evaluation (deferred computation)
 * - Trampoline Pattern (tail recursion optimization)
 * - Stream Pattern (infinite lazy sequences)
 * - Continuation Pattern (continuation-passing style)
 * - Closure Pattern (lexical scoping and state capture)
 * - Lens Pattern (immutable data access)
 * - Zipper Pattern (data structure navigation)
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Functional Programming Patterns ===");
        System.out.println("Based on Category Theory and Lambda Calculus Foundations");
        System.out.println();
        
        demonstrateMonadPattern();
        demonstrateEitherPattern();
        demonstrateHigherOrderFunctions();
        demonstrateFunctorPattern();
        demonstrateImmutableDataStructures();
        demonstrateLazyEvaluation();
        demonstrateTrampolinePattern();
        demonstrateStreamPattern();
        demonstrateContinuationPattern();
        demonstrateClosurePattern();
        demonstrateApplicativePattern();
        demonstrateStatePattern();
        demonstrateReaderPattern();
        demonstrateWriterPattern();
        demonstrateLensPattern();
        demonstrateIOPattern();
        demonstrateFreePattern();
        demonstrateZipperPattern();
        demonstrateComonadPattern();
        demonstrateFoldPattern();
        demonstrateUnfoldPattern();
        demonstrateMonoidPattern();
        demonstrateSemigroupPattern();
        demonstrateKleisliPattern();
        demonstrateProfunctorPattern();
        
        System.out.println("\n=== COMPLETE Functional Pattern Catalog (25 Total) ===");
        System.out.println("✅ Maybe/Either/IO Monads - null/error/side-effect handling");
        System.out.println("✅ State/Reader/Writer/Free Monads - stateful/contextual/DSL computations");
        System.out.println("✅ Applicative Functors - multi-argument functions");
        System.out.println("✅ Functors - mappable containers");
        System.out.println("✅ Higher-Order Functions - composition & reuse");
        System.out.println("✅ Immutable Data Structures - persistent collections");
        System.out.println("✅ Lazy Evaluation - deferred computation");
        System.out.println("✅ Trampoline - tail recursion optimization");
        System.out.println("✅ Streams - infinite lazy sequences");
        System.out.println("✅ Continuations - control flow abstraction");
        System.out.println("✅ Closures - lexical scoping & state capture");
        System.out.println("✅ Lens - immutable data access & modification");
        System.out.println("✅ Zipper - data structure navigation");
        System.out.println("✅ Function Combinators - composable operations");
        System.out.println("✅ Comonad - dual of monad, context extraction");
        System.out.println("✅ Fold/Catamorphism - data structure deconstruction");
        System.out.println("✅ Unfold/Anamorphism - data structure construction");
        System.out.println("✅ Monoid - associative operations with identity");
        System.out.println("✅ Semigroup - associative operations (no identity)");
        System.out.println("✅ Kleisli Arrow - monadic function composition");
        System.out.println("✅ Profunctor - contravariant/covariant mapping");
        
        System.out.println("\n🏆 ULTIMATE FUNCTIONAL PROGRAMMING MASTERY ACHIEVED!");
        System.out.println("🎆 All 24 patterns from Category Theory & Lambda Calculus implemented!");
        System.out.println("📚 Complete mathematical foundations of functional programming!");
    }
    
    private static void demonstrateMonadPattern() {
        System.out.println("--- Monad Pattern (Maybe) ---");
        
        Maybe<String> some = Maybe.of("Hello");
        Maybe<String> none = Maybe.none();
        
        Maybe<Integer> result1 = some
            .map(String::length)
            .filter(len -> len > 3)
            .map(len -> len * 2);
        
        Maybe<Integer> result2 = none
            .map(String::length)
            .filter(len -> len > 3)
            .map(len -> len * 2);
        
        System.out.println("Some result: " + result1 + " = " + result1.orElse(0));
        System.out.println("None result: " + result2 + " = " + result2.orElse(0));
    }
    
    private static void demonstrateHigherOrderFunctions() {
        System.out.println("\n--- Higher-Order Functions ---");
        
        // Function composition
        Function<String, Integer> length = String::length;
        Function<Integer, Integer> square = x -> x * x;
        Function<String, Integer> lengthSquared = HigherOrderFunctions.compose(square, length);
        
        System.out.println("Length squared of 'Hello': " + lengthSquared.apply("Hello"));
        
        // Currying
        HigherOrderFunctions.Function2<Integer, Integer, Integer> add = (a, b) -> a + b;
        Function<Integer, Function<Integer, Integer>> curriedAdd = HigherOrderFunctions.curry(add);
        Function<Integer, Integer> add5 = curriedAdd.apply(5);
        
        System.out.println("Curried add 5 + 3: " + add5.apply(3));
        
        // Memoization
        Function<Integer, Integer> fibonacci = HigherOrderFunctions.memoize(App::fib);
        System.out.println("Memoized fibonacci(10): " + fibonacci.apply(10));
    }
    
    private static void demonstrateFunctorPattern() {
        System.out.println("\n--- Functor Pattern ---");
        
        Box<String> box = new Box<>("Hello");
        Box<Integer> mappedBox = box
            .map(String::length)
            .map(len -> len * 2);
        
        System.out.println("Original box: " + box);
        System.out.println("Mapped box: " + mappedBox);
    }
    
    private static void demonstrateImmutableDataStructures() {
        System.out.println("\n--- Immutable Data Structures ---");
        
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        ImmutableList<Integer> doubled = list.map(x -> x * 2);
        ImmutableList<Integer> evens = list.filter(x -> x % 2 == 0);
        ImmutableList<Integer> extended = list.prepend(0);
        
        System.out.println("Original: " + list);
        System.out.println("Doubled: " + doubled);
        System.out.println("Evens: " + evens);
        System.out.println("Extended: " + extended);
    }
    
    private static void demonstrateLazyEvaluation() {
        System.out.println("\n--- Lazy Evaluation ---");
        
        Lazy<String> lazy = Lazy.of(() -> {
            System.out.println("Expensive computation...");
            return "Computed Value";
        });
        
        System.out.println("Lazy created: " + lazy);
        System.out.println("Is computed: " + lazy.isComputed());
        
        Lazy<Integer> lazyLength = lazy.map(String::length);
        System.out.println("Mapped lazy: " + lazyLength);
        
        System.out.println("Getting value: " + lazy.get());
        System.out.println("Getting length: " + lazyLength.get());
        System.out.println("Is computed: " + lazy.isComputed());
    }
    
    private static void demonstrateEitherPattern() {
        AppExtended.demonstrateEitherPattern();
    }
    
    private static void demonstrateTrampolinePattern() {
        AppExtended.demonstrateTrampolinePattern();
    }
    
    private static void demonstrateStreamPattern() {
        AppExtended.demonstrateStreamPattern();
    }
    
    private static void demonstrateContinuationPattern() {
        AppExtended.demonstrateContinuationPattern();
    }
    
    private static void demonstrateClosurePattern() {
        System.out.println("\n--- Closure Pattern ---");
        
        // Counter closure with state
        java.util.function.Supplier<Integer> counter = Closure.createCounter(0);
        System.out.println("Counter: " + counter.get() + ", " + counter.get() + ", " + counter.get());
        
        // Multiplier closure
        java.util.function.Function<Integer, Integer> triple = Closure.createMultiplier(3);
        System.out.println("Triple 7: " + triple.apply(7));
        
        // Processor closure with configuration
        java.util.function.Function<String, String> processor = Closure.createProcessor("[", "]", true);
        System.out.println("Processed: " + processor.apply("hello"));
        
        // Closure demo with lexical scoping
        Closure.ClosureDemo demo = new Closure.ClosureDemo("Alice");
        java.util.function.Function<String, String> greeter = demo.createGreeter();
        System.out.println(greeter.apply("Bob"));
        
        System.out.println("Closures capture and retain lexical scope!");
    }
    
    private static void demonstrateApplicativePattern() {
        AppAdvanced.demonstrateApplicativePattern();
    }
    
    private static void demonstrateStatePattern() {
        AppAdvanced.demonstrateStatePattern();
    }
    
    private static void demonstrateReaderPattern() {
        AppAdvanced.demonstrateReaderPattern();
    }
    
    private static void demonstrateWriterPattern() {
        AppAdvanced.demonstrateWriterPattern();
    }
    
    private static void demonstrateLensPattern() {
        AppAdvanced.demonstrateLensPattern();
    }
    
    private static void demonstrateIOPattern() {
        AppAdvanced.demonstrateIOPattern();
    }
    
    private static void demonstrateFreePattern() {
        AppAdvanced.demonstrateFreePattern();
    }
    
    private static void demonstrateZipperPattern() {
        AppAdvanced.demonstrateZipperPattern();
    }
    
    private static void demonstrateComonadPattern() {
        AppAdvanced.demonstrateComonadPattern();
    }
    
    private static void demonstrateFoldPattern() {
        AppAdvanced.demonstrateFoldPattern();
    }
    
    private static void demonstrateUnfoldPattern() {
        AppAdvanced.demonstrateUnfoldPattern();
    }
    
    private static void demonstrateMonoidPattern() {
        AppAdvanced.demonstrateMonoidPattern();
    }
    
    private static void demonstrateSemigroupPattern() {
        AppAdvanced.demonstrateSemigroupPattern();
    }
    
    private static void demonstrateKleisliPattern() {
        AppAdvanced.demonstrateKleisliPattern();
    }
    
    private static void demonstrateProfunctorPattern() {
        AppAdvanced.demonstrateProfunctorPattern();
    }
    
    private static int fib(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }
}