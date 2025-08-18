package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Advanced functional pattern demonstrations
 */
public class AppAdvanced {
    
    public static void demonstrateApplicativePattern() {
        System.out.println("\n--- Applicative Functor Pattern ---");
        
        Applicative<Integer> value1 = Applicative.pure(5);
        Applicative<Integer> value2 = Applicative.pure(3);
        Applicative<Function<Integer, Integer>> addFunc = Applicative.pure((Integer x) -> x + 10);
        
        Applicative<Integer> result = value1.apply(addFunc);
        System.out.println("Applied function: " + result);
    }
    
    public static void demonstrateStatePattern() {
        System.out.println("\n--- State Monad Pattern ---");
        
        // Counter state operations
        State<Integer, String> increment = State.<Integer>get()
            .flatMap(count -> State.put(count + 1)
            .flatMap(v -> State.pure("Incremented to " + (count + 1))));
        
        State<Integer, String> doubleState = State.<Integer>get()
            .flatMap(count -> State.put(count * 2)
            .flatMap(v -> State.pure("Doubled to " + (count * 2))));
        
        State<Integer, String> combined = increment.flatMap(msg1 -> 
            doubleState.map(msg2 -> msg1 + ", " + msg2));
        
        State.Pair<String, Integer> result = combined.run(5);
        System.out.println("State result: " + result.first() + ", final state: " + result.second());
    }
    
    public static void demonstrateReaderPattern() {
        System.out.println("\n--- Reader Monad Pattern ---");
        
        // Configuration-based computation
        Reader<String, String> greet = Reader.<String>ask()
            .map(name -> "Hello, " + name + "!");
        
        Reader<String, String> shout = greet.map(String::toUpperCase);
        
        String result = shout.run("World");
        System.out.println("Reader result: " + result);
    }
    
    public static void demonstrateWriterPattern() {
        System.out.println("\n--- Writer Monad Pattern ---");
        
        Writer<String, Integer> computation1 = Writer.tell(5, "Started with 5; ");
        Writer<String, Integer> computation2 = new Writer<>(10, "Added 5; ");
        
        Function<String, Function<String, String>> combiner = log1 -> log2 -> log1 + log2;
        Writer<String, Integer> result = computation1.flatMap(
            x -> computation2.map(y -> x + y), 
            combiner
        );
        
        System.out.println("Writer result: " + result);
    }
    
    public static void demonstrateLensPattern() {
        System.out.println("\n--- Lens Pattern ---");
        
        // Simple person record
        record Person(String name, int age) {}
        
        Lens<Person, String> nameLens = Lens.lens(
            Person::name,
            person -> newName -> new Person(newName, person.age())
        );
        
        Lens<Person, Integer> ageLens = Lens.lens(
            Person::age,
            person -> newAge -> new Person(person.name(), newAge)
        );
        
        Person person = new Person("John", 30);
        Person updated = nameLens.set(person, "Jane");
        Person aged = ageLens.modify(updated, age -> age + 1);
        
        System.out.println("Original: " + person);
        System.out.println("Updated name: " + updated);
        System.out.println("Aged: " + aged);
    }
    
    public static void demonstrateIOPattern() {
        System.out.println("\n--- IO Monad Pattern ---");
        
        IO<String> greeting = IO.pure("Hello")
            .map(s -> s + ", World!")
            .flatMap(s -> IO.println(s).map(v -> s));
        
        System.out.println("IO created (not executed yet)");
        String result = greeting.unsafeRun();
        System.out.println("IO executed, result: " + result);
    }

    public static void demonstrateFreePattern() {
        System.out.println("\n--- Free Monad Pattern ---");

        Free<Integer> step1 = Free.pure(5);
        Free<String> step2 = step1.flatMap(x -> Free.pure("Operation: " + x));
        Free<Integer> program = step2.flatMap(s -> Free.pure(42));

        System.out.println("Free monad program (interpreted): " + program.interpret());
    }

    public static void demonstrateZipperPattern() {
        System.out.println("\n--- Zipper Pattern ---");
        
        java.util.List<Integer> list = java.util.List.of(1, 2, 3, 4, 5);
        Zipper<Integer> zipper = Zipper.fromList(list);
        
        System.out.println("Initial: " + zipper);
        System.out.println("Focus: " + zipper.focus());
        
        Zipper<Integer> moved = zipper.moveRight().moveRight();
        System.out.println("Moved right twice: " + moved);
        System.out.println("New focus: " + moved.focus());
        
        Zipper<Integer> modified = moved.update(x -> x * 10);
        System.out.println("Modified focus: " + modified);
        System.out.println("Back to list: " + modified.toList());
    }
    
    public static void demonstrateComonadPattern() {
        System.out.println("\n--- Comonad Pattern ---");
        
        Comonad<Integer> comonad = Comonad.identity(42);
        System.out.println("Comonad: " + comonad);
        System.out.println("Extract: " + comonad.extract());
        
        Comonad<String> mapped = comonad.map(x -> "Value: " + x);
        System.out.println("Mapped: " + mapped.extract());
        
        Comonad<Integer> extended = comonad.extend(c -> c.extract() * 2);
        System.out.println("Extended: " + extended.extract());
    }
    
    public static void demonstrateFoldPattern() {
        System.out.println("\n--- Fold Pattern ---");
        
        ImmutableList<Integer> numbers = ImmutableList.of(1, 2, 3, 4, 5);
        
        Integer sum = Fold.foldLeft(numbers, 0, Integer::sum);
        Integer product = Fold.foldRight(numbers, 1, (a, b) -> a * b);
        Integer max = Fold.reduce(numbers, Integer::max);
        
        System.out.println("List: " + numbers);
        System.out.println("Sum: " + sum);
        System.out.println("Product: " + product);
        System.out.println("Max: " + max);
    }

    public static void demonstrateUnfoldPattern() {
        System.out.println("\n--- Unfold Pattern ---");

        ImmutableList<Integer> range = Unfold.range(1, 6);
        System.out.println("Range 1-5: " + range);

        // First 5 powers starting from 2: 2,4,8,16,32
        ImmutableList<Integer> powers = Unfold.generate(2, x -> x * 2, 5);
        System.out.println("Powers of 2: " + powers);

        // Fibonacci numbers for n = 1..8
        ImmutableList<Integer> fibonacci = Unfold.generate(1, x -> x + 1, 8).map(AppAdvanced::fib);
        System.out.println("Fibonacci 1..8: " + fibonacci);
    }

    private static int fib(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }
    
    public static void demonstrateMonoidPattern() {
        System.out.println("\n--- Monoid Pattern ---");
        
        ImmutableList<Integer> numbers = ImmutableList.of(1, 2, 3, 4, 5);
        ImmutableList<String> words = ImmutableList.of("Hello", " ", "World", "!");
        
        Monoidal<Integer> addMonoid = Monoidal.integerAddition();
        Monoidal<Integer> mulMonoid = Monoidal.integerMultiplication();
        Monoidal<String> strMonoid = Monoidal.stringConcatenation();
        
        System.out.println("Sum: " + addMonoid.combineAll(numbers));
        System.out.println("Product: " + mulMonoid.combineAll(numbers));
        System.out.println("Concat: " + strMonoid.combineAll(words));
    }
    
    public static void demonstrateSemigroupPattern() {
        System.out.println("\n--- Semigroup Pattern ---");
        
        ImmutableList<Integer> numbers = ImmutableList.of(3, 1, 4, 1, 5);
        
        Semigroup<Integer> maxSemi = Semigroup.integerMax();
        Semigroup<Integer> minSemi = Semigroup.integerMin();
        
        System.out.println("Numbers: " + numbers);
        System.out.println("Max: " + maxSemi.combineAll(numbers));
        System.out.println("Min: " + minSemi.combineAll(numbers));
    }

    public static void demonstrateKleisliPattern() {
        System.out.println("\n--- Kleisli Arrow Pattern ---");

        Kleisli<String, Integer> parseInt = Kleisli.of(s -> {
            try {
                return Maybe.of(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                return Maybe.none();
            }
        });

        Kleisli<Integer, Integer> square = Kleisli.of(x -> Maybe.of(x * x));

        Maybe<Integer> result1 = parseInt.apply("42");
        Maybe<Integer> result2 = parseInt.apply("invalid");

        System.out.println("Parse '42': " + result1);
        System.out.println("Parse 'invalid': " + result2);

        if (result1.isPresent()) {
            Maybe<Integer> squared = square.apply(result1.orElse(0));
            System.out.println("Squared: " + squared);
        }
    }

    public static void demonstrateProfunctorPattern() {
        System.out.println("\n--- Profunctor Pattern ---");

        Profunctor<String, Integer> stringLength = Profunctor.of(String::length);

        Profunctor<Integer, String> intToString = stringLength.dimap(
                Object::toString,
                len -> "Length: " + len
        );

        String result = intToString.apply(12345);
        System.out.println("Profunctor result: " + result);
    }
}