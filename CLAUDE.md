# CLAUDE.md - AI Assistant Guide for Design Patterns Repository

**Last Updated:** 2025-11-13
**Repository:** Ultimate Design Patterns Collection
**Version:** 1.0-SNAPSHOT

## Table of Contents
- [Repository Overview](#repository-overview)
- [Codebase Structure](#codebase-structure)
- [Development Conventions](#development-conventions)
- [Java Features & Requirements](#java-features--requirements)
- [Build System](#build-system)
- [Pattern Implementation Approach](#pattern-implementation-approach)
- [Testing Strategy](#testing-strategy)
- [Working with Patterns](#working-with-patterns)
- [Common Workflows](#common-workflows)
- [Key Locations](#key-locations)
- [Troubleshooting](#troubleshooting)

---

## Repository Overview

### What This Is
This is the **most comprehensive design patterns collection** ever assembled, containing **104 patterns** across all major programming paradigms:
- Object-Oriented Programming (GoF patterns, SOLID principles)
- Functional Programming (Category Theory, Lambda Calculus - 25 patterns)
- Enterprise Architecture (J2EE, microservices, domain-driven design)
- Modern Java (Pattern matching, virtual threads, sealed classes, records)

### Key Statistics
- **51 executable Maven modules**
- **104 total patterns** implemented
- **395+ test methods** with comprehensive coverage
- **Java 24** with bleeding-edge features
- **Multiple implementation approaches** per pattern (traditional, functional, enum-based, stream-based, record-based)

### Educational Purpose
This repository serves as a complete learning resource demonstrating:
1. Evolution from traditional OOP to modern functional approaches
2. Mathematical foundations (Category Theory, Lambda Calculus)
3. Enterprise architecture best practices
4. Modern Java feature adoption (Java 17-24)

---

## Codebase Structure

### Multi-Module Maven Project

```
udemy-design-Patterns-SOLID/
├── pom.xml                              # Parent POM (51 modules)
├── README.md                            # Comprehensive documentation
├── CLAUDE.md                            # This file
│
├── SOLID Principles (5 modules)
├── S/                                   # Single Responsibility
├── O/                                   # Open/Closed
├── L/                                   # Liskov Substitution
├── I/                                   # Interface Segregation
├── D/                                   # Dependency Inversion
│
├── GoF Behavioral Patterns (11 modules)
├── StrategyPattern/
├── ObserverPattern/
├── CommandPattern/
├── CommandPatternII/
├── StatePattern/
├── TemplatePattern/
├── IteratorPattern/
├── VisitorPattern/
├── ChainOfResponsibilityPattern/
├── MediatorPattern/
├── MementoPattern/
├── InterpreterPattern/
│
├── GoF Creational Patterns (5 modules)
├── SingletonPattern/
├── FactoryPattern/
├── AbstractFactoryPattern/
├── BuilderPattern/
├── PrototypePattern/
│
├── GoF Structural Patterns (8 modules)
├── AdapterPattern/
├── AdapterPatternII/
├── BridgePattern/
├── CompositePattern/
├── DecoratorPattern/
├── FacadePattern/
├── FlyweightPattern/
├── ProxyPattern/
│
├── Architectural Patterns (5 modules)
├── MVCPattern/
├── DAOPattern/
├── DTOPattern/
├── ServiceLocatorPattern/
├── NullObjectPattern/
│
├── Modern Enterprise Patterns (5 modules)
├── RepositoryPattern/
├── UnitOfWorkPattern/
├── SpecificationPattern/
├── BusinessDelegatePattern/
├── APIGatewayPattern/
│
├── J2EE Enterprise Patterns (5 modules)
├── SessionFacadePattern/
├── ValueObjectPattern/
├── FrontControllerPattern/
├── DependencyInjectionPattern/
├── TransferObjectAssemblerPattern/
│
├── Functional Programming (1 module, 25 patterns)
├── FunctionalProgrammingPatterns/
│   ├── Monads (7): Maybe, Either, IO, State, Reader, Writer, Free
│   ├── Functors (3): Functor, Applicative, Comonad
│   ├── Morphisms (2): Fold/Catamorphism, Unfold/Anamorphism
│   ├── Algebraic (2): Monoid, Semigroup
│   ├── Arrows (2): Kleisli, Profunctor
│   └── Core (9): Higher-Order Functions, Lazy, Trampoline, etc.
│
└── Modern Java Patterns (6 modules)
    ├── PatternMatchingPattern/
    ├── VirtualThreadPattern/
    ├── RecordBuilderPattern/
    ├── SealedHierarchyPattern/
    ├── StreamCollectorPattern/
    └── ModuleServicePattern/
```

### Typical Module Structure

```
PatternModule/
├── pom.xml                              # Module POM with exec plugin config
├── src/
│   ├── main/java/com/balazsholczer/pattern/
│   │   ├── App.java                     # Main entry point (exec.mainClass)
│   │   ├── [Pattern]Interface.java      # Core abstraction
│   │   ├── ConcreteImpl1.java          # Traditional implementation
│   │   ├── ConcreteImpl2.java          # Traditional implementation
│   │   ├── Manager.java                # Traditional context/coordinator
│   │   ├── FunctionalManager.java      # Functional approach
│   │   ├── EnumStrategyManager.java    # Enum-based approach
│   │   ├── StreamManager.java          # Stream-based approach
│   │   └── ModernDemo.java             # Modern Java features demo
│   └── test/java/com/balazsholczer/pattern/
│       ├── PatternTest.java            # Comprehensive tests
│       └── EquivalenceTest.java        # Verify all approaches match
```

---

## Development Conventions

### Naming Conventions

#### Module Names
- **Pattern modules:** `PatternNamePattern/` (e.g., `StrategyPattern/`, `ObserverPattern/`)
- **SOLID principles:** Single uppercase letters (`S/`, `O/`, `L/`, `I/`, `D/`)
- **Modern Java:** Descriptive names (`VirtualThreadPattern/`, `SealedHierarchyPattern/`)
- **Multi-pattern:** `FunctionalProgrammingPatterns/` (contains 25 patterns)

#### Package Names
**Convention:** `com.balazsholczer.{pattern-name-lowercase}`

**Examples:**
```
StrategyPattern/       → com.balazsholczer.strategy
ObserverPattern/       → com.balazsholczer.observer
PatternMatchingPattern/→ com.balazsholczer.patternmatching
VirtualThreadPattern/  → com.balazsholczer.virtualthread
S/                     → com.balazsholczer.solid
```

**Rule:** Hyphenated artifact IDs become single lowercase words in package names.

#### Class Naming
- **Main class:** Always `App.java` (unless specified in `exec.mainClass`)
- **Interfaces:** `Strategy`, `Observer`, `Command` (pattern name)
- **Implementations:** Descriptive names (`Add`, `Multiply`, `ConcreteObserver`)
- **Managers/Contexts:** `Manager`, `Context`, `FunctionalManager`, `EnumStrategyManager`
- **Tests:** `PatternTest.java`, `PatternNameTest.java`

### Code Organization

#### Multiple Implementation Approaches
Each pattern demonstrates 3-5 different implementations:

1. **Traditional OOP** - Classic GoF implementation
   ```java
   public class Manager {
       private Strategy strategy;
       public void setStrategy(Strategy strategy) { ... }
   }
   ```

2. **Functional** - Lambdas and functional interfaces
   ```java
   public class FunctionalManager {
       private BinaryOperator<Integer> operation;
       public void setOperation(BinaryOperator<Integer> op) { ... }
   }
   ```

3. **Enum-based** - Type-safe enums implementing functional interfaces
   ```java
   public enum Operation implements BinaryOperator<Integer> {
       ADD(Integer::sum),
       MULTIPLY((a, b) -> a * b);
   }
   ```

4. **Stream-based** - Leveraging Java Streams API
   ```java
   public class StreamManager {
       public int execute(Stream<Integer> values) { ... }
   }
   ```

5. **Record-based** - Using Java records for immutable data
   ```java
   public record Person(String name, int age) {
       public static Builder builder() { ... }
   }
   ```

### Maven POM Structure

#### Parent POM (`pom.xml`)
```xml
<groupId>com.balazsholczer</groupId>
<artifactId>udemy-design-patterns-solid</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>pom</packaging>

<properties>
    <maven.compiler.source>24</maven.compiler.source>
    <maven.compiler.target>24</maven.compiler.target>
</properties>

<modules>
    <module>StrategyPattern</module>
    <!-- ... 51 modules total -->
</modules>
```

#### Module POM (typical pattern)
```xml
<parent>
    <groupId>com.balazsholczer</groupId>
    <artifactId>udemy-design-patterns-solid</artifactId>
    <version>1.0-SNAPSHOT</version>
</parent>

<artifactId>strategy-pattern</artifactId>
<packaging>jar</packaging>

<properties>
    <exec.mainClass>com.balazsholczer.strategy.App</exec.mainClass>
</properties>

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <mainClass>${exec.mainClass}</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

---

## Java Features & Requirements

### Java Version
**Target:** Java 24 (bleeding edge)
```xml
<maven.compiler.source>24</maven.compiler.source>
<maven.compiler.target>24</maven.compiler.target>
```

**Minimum recommended:** Java 21 (for pattern matching, virtual threads, record patterns)

### Modern Java Features Used

#### 1. Records (Java 16+)
```java
// Immutable data classes
public record Person(String name, int age, String email, String address) {
    // Compact constructor for validation
    public Person {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
    }

    // Static factory method
    public static Builder builder() {
        return new Builder();
    }
}

// Records implementing interfaces
public record Circle(double radius) implements Shape {
    @Override
    public double area() { return Math.PI * radius * radius; }
}
```

#### 2. Sealed Classes (Java 17+)
```java
// Controlled inheritance hierarchies
public sealed interface Shape permits Circle, Rectangle, Triangle {}

public record Circle(double radius) implements Shape {}
public record Rectangle(double width, double height) implements Shape {}
public record Triangle(double base, double height) implements Shape {}
```

#### 3. Pattern Matching (Java 21+)
```java
// instanceof patterns
if (shape instanceof Circle circle) {
    return Math.PI * circle.radius() * circle.radius();
}

// Switch expressions with pattern matching
return switch (shape) {
    case Circle(var radius) -> Math.PI * radius * radius;
    case Rectangle(var w, var h) -> w * h;
    case Triangle(var b, var h) -> 0.5 * b * h;
};

// Pattern guards
return switch (shape) {
    case Circle(var r) when r > 10 -> "Large circle";
    case Circle(var r) -> "Small circle";
    case Rectangle(var w, var h) when w == h -> "Square";
    case Rectangle(var w, var h) -> "Rectangle";
};

// Nested patterns
case Line(Point(var x1, var y1), Point(var x2, var y2)) ->
    Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));

// Null-safe matching
case null -> "Null object";
```

#### 4. Virtual Threads (Java 21+)
```java
// Project Loom virtual threads
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10000)
        .forEach(i -> executor.submit(() -> {
            // Task execution
        }));
}

// Structured concurrency
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Future<String> user = scope.fork(() -> fetchUser());
    Future<Integer> order = scope.fork(() -> fetchOrder());
    scope.join();
    scope.throwIfFailed();
    return new Result(user.resultNow(), order.resultNow());
}
```

#### 5. Functional Programming
```java
// Lambda expressions
BinaryOperator<Integer> add = (a, b) -> a + b;

// Method references
BinaryOperator<Integer> multiply = (a, b) -> a * b;
// or
Function<String, Integer> parser = Integer::parseInt;

// Stream API
List<String> result = items.stream()
    .filter(item -> item.length() > 5)
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Optional for null safety
Optional<User> user = findUser(id);
return user.map(User::getName).orElse("Unknown");
```

#### 6. Enum as Functional Interface
```java
public enum Operation implements BinaryOperator<Integer> {
    ADD(Integer::sum),
    MULTIPLY((a, b) -> a * b),
    SUBTRACT((a, b) -> a - b),
    DIVIDE((a, b) -> a / b);

    private final BinaryOperator<Integer> operation;

    Operation(BinaryOperator<Integer> operation) {
        this.operation = operation;
    }

    @Override
    public Integer apply(Integer a, Integer b) {
        return operation.apply(a, b);
    }
}
```

#### 7. Text Blocks (Java 15+)
```java
String json = """
    {
        "name": "John",
        "age": 30
    }
    """;
```

---

## Build System

### Maven Configuration

#### Parent POM Properties
```xml
<properties>
    <maven.compiler.source>24</maven.compiler.source>
    <maven.compiler.target>24</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <junit.version>5.9.2</junit.version>
    <maven.compiler.plugin.version>3.11.0</maven.compiler.plugin.version>
    <maven.surefire.plugin.version>3.0.0</maven.surefire.plugin.version>
</properties>
```

#### Maven Profiles

##### 1. `run-all-patterns` Profile
Executes all 51 pattern demonstrations sequentially.

```bash
mvn clean compile -Prun-all-patterns
```

**What it does:**
- Triggers `exec-maven-plugin` during compile phase
- Runs the main class (`${exec.mainClass}`) of each module
- Displays output for all patterns

##### 2. `test-all-patterns` Profile
Runs comprehensive test suites across all modules.

```bash
mvn clean test -Ptest-all-patterns
```

**Configuration:**
```xml
<configuration>
    <includes>
        <include>**/*Test.java</include>
        <include>**/*Tests.java</include>
    </includes>
</configuration>
```

### Common Maven Commands

```bash
# Build all modules
mvn clean compile

# Install all modules to local repository
mvn clean install

# Run all pattern demonstrations (51 patterns)
mvn clean compile -Prun-all-patterns

# Run all tests (395+ test methods)
mvn test

# Run all tests with detailed reporting
mvn clean test -Ptest-all-patterns

# Run individual pattern
cd StrategyPattern
mvn exec:java

# Run specific module tests
cd StrategyPattern
mvn test

# Build single module from root
mvn clean compile -pl StrategyPattern

# Build module and dependencies
mvn clean compile -pl StrategyPattern -am

# Skip tests
mvn clean install -DskipTests

# Run tests with specific test
mvn test -Dtest=StrategyTest

# Run tests with pattern
mvn test -Dtest=*Strategy*
```

### Exec Plugin Configuration

Each module configures the exec plugin to run its main class:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <mainClass>${exec.mainClass}</mainClass>
    </configuration>
    <executions>
        <execution>
            <id>run-pattern</id>
            <goals>
                <goal>java</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

## Pattern Implementation Approach

### Design Philosophy

Each pattern demonstrates **multiple implementation paradigms** to show:
1. **Evolution** from traditional OOP to modern functional approaches
2. **Equivalence** - All approaches solve the same problem
3. **Trade-offs** - Performance, readability, maintainability
4. **Modern Java** - Leveraging language features appropriately

### Implementation Checklist

When implementing a new pattern, include:

- [ ] **Traditional OOP implementation**
  - Interface definitions
  - Concrete implementations
  - Context/Manager classes

- [ ] **Functional implementation**
  - Using lambdas and method references
  - Functional interfaces (`Function`, `BinaryOperator`, etc.)
  - Higher-order functions

- [ ] **Enum-based implementation** (where applicable)
  - Enum implementing functional interface
  - Type-safe operations

- [ ] **Stream-based implementation** (where applicable)
  - Leveraging Java Streams API
  - Collectors and reduction operations

- [ ] **Record-based implementation** (where applicable)
  - Immutable data structures
  - Builder pattern for records

- [ ] **Modern Java features**
  - Pattern matching (sealed classes, switch expressions)
  - Virtual threads (for concurrent patterns)
  - Text blocks (for data/config)

- [ ] **Main demonstration class** (`App.java`)
  - Shows all implementations in action
  - Clear console output

- [ ] **Comprehensive tests**
  - Test each implementation approach
  - Verify equivalence between approaches
  - Edge cases and error handling

### Example: Strategy Pattern Implementation

```java
// 1. Traditional OOP
public interface Strategy {
    int execute(int a, int b);
}

public class Add implements Strategy {
    @Override
    public int execute(int a, int b) { return a + b; }
}

public class Manager {
    private Strategy strategy;
    public void setStrategy(Strategy strategy) { this.strategy = strategy; }
    public int execute(int a, int b) { return strategy.execute(a, b); }
}

// 2. Functional
public class FunctionalManager {
    private BinaryOperator<Integer> operation;
    public void setOperation(BinaryOperator<Integer> op) { this.operation = op; }
    public int execute(int a, int b) { return operation.apply(a, b); }
}

// Usage: manager.setOperation(Integer::sum);

// 3. Enum-based
public enum Operation implements BinaryOperator<Integer> {
    ADD(Integer::sum),
    MULTIPLY((a, b) -> a * b);

    private final BinaryOperator<Integer> operation;
    Operation(BinaryOperator<Integer> op) { this.operation = op; }

    @Override
    public Integer apply(Integer a, Integer b) {
        return operation.apply(a, b);
    }
}

// 4. Stream-based
public class StreamStrategyManager {
    public int aggregate(List<Integer> values, BinaryOperator<Integer> op) {
        return values.stream().reduce(op).orElse(0);
    }
}
```

---

## Testing Strategy

### Framework
- **JUnit 5 (Jupiter)** - Version 5.9.2
- All tests use JUnit 5 annotations and assertions

### Test Organization

```java
package com.balazsholczer.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    @BeforeEach
    void setUp() {
        // Test setup
    }

    @Test
    void testTraditionalStrategy() {
        Manager manager = new Manager();
        manager.setStrategy(new Add());
        assertEquals(7, manager.execute(3, 4));
    }

    @Test
    void testFunctionalManager() {
        FunctionalManager manager = new FunctionalManager();
        manager.setOperation(Integer::sum);
        assertEquals(7, manager.execute(3, 4));
    }

    @Test
    void testEnumStrategy() {
        assertEquals(7, Operation.ADD.apply(3, 4));
        assertEquals(12, Operation.MULTIPLY.apply(3, 4));
    }

    @Test
    void testEquivalence() {
        // Verify all approaches produce same result
        Manager traditional = new Manager();
        traditional.setStrategy(new Add());

        FunctionalManager functional = new FunctionalManager();
        functional.setOperation(Integer::sum);

        int result1 = traditional.execute(3, 4);
        int result2 = functional.execute(3, 4);
        int result3 = Operation.ADD.apply(3, 4);

        assertEquals(result1, result2);
        assertEquals(result2, result3);
    }
}
```

### Testing Principles

1. **Equivalence Testing**
   - All implementation approaches must produce identical results
   - Test verifies traditional == functional == enum-based == stream-based

2. **Comprehensive Coverage**
   - Test each implementation variant separately
   - Test edge cases (empty, null, boundary values)
   - Test error conditions

3. **Functional Laws** (for functional patterns)
   - **Identity:** `monad.flatMap(x -> unit(x)) == monad`
   - **Composition:** `monad.flatMap(f).flatMap(g) == monad.flatMap(x -> f(x).flatMap(g))`
   - **Functor laws:** `fmap id == id`, `fmap (f . g) == fmap f . fmap g`

4. **Clear Test Names**
   - Use descriptive names: `testTraditionalStrategy`, `testFunctionalManager`
   - Test method names describe what is being tested

### Test Execution

```bash
# Run all tests
mvn test

# Run tests for specific module
cd StrategyPattern && mvn test

# Run specific test class
mvn test -Dtest=StrategyTest

# Run specific test method
mvn test -Dtest=StrategyTest#testTraditionalStrategy

# Run all tests with pattern
mvn test -Dtest=*Strategy*

# Run tests with detailed output
mvn test -Ptest-all-patterns
```

---

## Working with Patterns

### Adding a New Pattern

#### Step 1: Create Module Directory
```bash
mkdir NewPatternPattern
cd NewPatternPattern
```

#### Step 2: Create Module POM
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.balazsholczer</groupId>
        <artifactId>udemy-design-patterns-solid</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>new-pattern-pattern</artifactId>
    <packaging>jar</packaging>

    <properties>
        <exec.mainClass>com.balazsholczer.newpattern.App</exec.mainClass>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>${exec.mainClass}</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

#### Step 3: Create Directory Structure
```bash
mkdir -p src/main/java/com/balazsholczer/newpattern
mkdir -p src/test/java/com/balazsholczer/newpattern
```

#### Step 4: Implement Pattern
Create classes following the multiple implementation approach:
- `App.java` - Main demonstration
- Interface/abstract classes
- Concrete implementations
- Functional variants
- Modern Java variants

#### Step 5: Write Tests
Create `NewPatternTest.java` with comprehensive test coverage.

#### Step 6: Update Parent POM
Add module to parent `pom.xml`:
```xml
<modules>
    <!-- ... existing modules ... -->
    <module>NewPatternPattern</module>
</modules>
```

#### Step 7: Build and Test
```bash
# From root
mvn clean compile -pl NewPatternPattern
mvn test -pl NewPatternPattern

# Run the pattern
cd NewPatternPattern
mvn exec:java
```

### Modifying Existing Patterns

#### Step 1: Understand Current Implementation
```bash
# Read the pattern code
cd StrategyPattern
ls -R src/

# Run existing tests
mvn test

# Run the demo
mvn exec:java
```

#### Step 2: Make Changes
- Maintain backward compatibility where possible
- Follow existing naming conventions
- Update tests to reflect changes

#### Step 3: Verify Changes
```bash
# Run tests
mvn test

# Run demonstration
mvn exec:java

# Run all tests to ensure no breakage
cd ..
mvn test
```

#### Step 4: Update Documentation
- Update inline comments
- Update README.md if needed
- Update this CLAUDE.md if conventions change

### Refactoring Patterns

When refactoring, maintain:
1. **All implementation approaches** (don't remove traditional, functional, etc.)
2. **Test coverage** - Update tests to match refactoring
3. **Equivalence** - Ensure all approaches still produce same results
4. **API compatibility** - Don't break existing usage patterns

---

## Common Workflows

### Workflow 1: Verify All Patterns Work

```bash
# From repository root
git pull origin main

# Build everything
mvn clean compile

# Run all pattern demonstrations
mvn clean compile -Prun-all-patterns

# Run all tests
mvn clean test -Ptest-all-patterns

# Verify success
echo "All patterns working: $?"
```

### Workflow 2: Add Modern Java Feature to Existing Pattern

```bash
# Example: Adding record-based approach to Strategy pattern
cd StrategyPattern

# 1. Create new record-based implementation
cat > src/main/java/com/balazsholczer/strategy/RecordOperation.java << 'EOF'
package com.balazsholczer.strategy;

import java.util.function.BinaryOperator;

public record RecordOperation(String name, BinaryOperator<Integer> operation) {
    public int execute(int a, int b) {
        return operation.apply(a, b);
    }
}
EOF

# 2. Update App.java to demonstrate
# (manually edit App.java to add demonstration)

# 3. Add tests
# (edit StrategyTest.java to add record-based tests)

# 4. Test
mvn test

# 5. Run demo
mvn exec:java

# 6. Commit changes
git add .
git commit -m "Add record-based implementation to Strategy pattern"
```

### Workflow 3: Fix Compilation Error

```bash
# 1. Identify failing module
mvn clean compile | grep "BUILD FAILURE"

# 2. Go to failing module
cd FailingModule

# 3. Compile with detailed errors
mvn clean compile -X

# 4. Fix the error
# (edit source files)

# 5. Verify fix
mvn clean compile

# 6. Run tests
mvn test

# 7. Verify all modules still work
cd ..
mvn clean compile
```

### Workflow 4: Update Java Version

```bash
# 1. Update parent POM
# Edit pom.xml:
#   <maven.compiler.source>25</maven.compiler.source>
#   <maven.compiler.target>25</maven.compiler.target>

# 2. Build all modules
mvn clean compile

# 3. Fix any compatibility issues
# (modules will fail to compile if issues exist)

# 4. Run all tests
mvn clean test

# 5. Update CLAUDE.md
# Document new Java version and features available

# 6. Commit
git add pom.xml CLAUDE.md
git commit -m "Update to Java 25"
```

### Workflow 5: Run Pattern Subset

```bash
# Run only SOLID principles
mvn clean compile -pl S,O,L,I,D

# Run only GoF Behavioral patterns
mvn clean compile -pl StrategyPattern,ObserverPattern,CommandPattern,StatePattern

# Run only Modern Java patterns
mvn clean compile -pl PatternMatchingPattern,VirtualThreadPattern,SealedHierarchyPattern

# Test specific category
mvn test -pl RepositoryPattern,UnitOfWorkPattern,SpecificationPattern
```

---

## Key Locations

### Important Files

| File | Purpose |
|------|---------|
| `/pom.xml` | Parent POM with all 51 modules |
| `/README.md` | Complete pattern documentation |
| `/CLAUDE.md` | This file - AI assistant guide |
| `/{Pattern}/pom.xml` | Module POM with exec config |
| `/{Pattern}/src/main/java/.../App.java` | Main demonstration class |
| `/{Pattern}/src/test/java/.../Test.java` | Test suite |

### Package Structure Map

```
com.balazsholczer
├── solid                          # SOLID principles (S, O, L, I, D)
├── strategy                       # StrategyPattern
├── observer                       # ObserverPattern
├── command                        # CommandPattern
├── state                          # StatePattern
├── template                       # TemplatePattern
├── iterator                       # IteratorPattern
├── visitor                        # VisitorPattern
├── chainofresponsibility         # ChainOfResponsibilityPattern
├── mediator                       # MediatorPattern
├── memento                        # MementoPattern
├── interpreter                    # InterpreterPattern
├── singleton                      # SingletonPattern
├── factory                        # FactoryPattern
├── abstractfactory               # AbstractFactoryPattern
├── builder                        # BuilderPattern
├── prototype                      # PrototypePattern
├── adapter                        # AdapterPattern / AdapterPatternII
├── bridge                         # BridgePattern
├── composite                      # CompositePattern
├── decorator                      # DecoratorPattern
├── facade                         # FacadePattern
├── flyweight                      # FlyweightPattern
├── proxy                          # ProxyPattern
├── mvc                            # MVCPattern
├── dao                            # DAOPattern
├── dto                            # DTOPattern
├── servicelocator                # ServiceLocatorPattern
├── nullobject                    # NullObjectPattern
├── repository                     # RepositoryPattern
├── unitofwork                    # UnitOfWorkPattern
├── specification                 # SpecificationPattern
├── businessdelegate              # BusinessDelegatePattern
├── apigateway                    # APIGatewayPattern
├── sessionfacade                 # SessionFacadePattern
├── valueobject                   # ValueObjectPattern
├── frontcontroller               # FrontControllerPattern
├── dependencyinjection           # DependencyInjectionPattern
├── transferobjectassembler       # TransferObjectAssemblerPattern
├── functional                     # FunctionalProgrammingPatterns
├── patternmatching               # PatternMatchingPattern
├── virtualthread                 # VirtualThreadPattern
├── recordbuilder                 # RecordBuilderPattern
├── sealedhierarchy              # SealedHierarchyPattern
├── streamcollector              # StreamCollectorPattern
└── moduleservice                # ModuleServicePattern
```

### Finding Things

```bash
# Find all App.java main classes
find . -name "App.java" -type f

# Find all test files
find . -name "*Test.java" -type f

# Find all POMs
find . -name "pom.xml" -type f

# Search for specific pattern in code
grep -r "sealed interface" --include="*.java"

# Find usage of specific Java feature
grep -r "virtual thread" --include="*.java" -i

# Count total lines of code
find . -name "*.java" -type f | xargs wc -l
```

---

## Troubleshooting

### Common Issues

#### Issue 1: Compilation Fails with Java Version Error

**Symptom:**
```
[ERROR] Source option 24 is not supported. Use 21 or lower.
```

**Solution:**
```bash
# Check Java version
java -version

# Ensure Java 24 (or 21+) is installed
# Update JAVA_HOME if needed
export JAVA_HOME=/path/to/java24

# Verify Maven uses correct Java
mvn -version
```

#### Issue 2: Module Not Found

**Symptom:**
```
[ERROR] Could not find artifact com.balazsholczer:strategy-pattern:jar:1.0-SNAPSHOT
```

**Solution:**
```bash
# Install all modules
mvn clean install

# Or install specific module
cd StrategyPattern
mvn clean install
```

#### Issue 3: Tests Fail After Code Change

**Symptom:**
```
[ERROR] Tests run: 10, Failures: 2, Errors: 0, Skipped: 0
```

**Solution:**
```bash
# Run tests with detailed output
mvn test -Dtest=FailingTest

# Check test assertions
# Verify equivalence between implementations
# Update tests if behavior intentionally changed

# Re-run all tests
mvn clean test
```

#### Issue 4: Exec Plugin Cannot Find Main Class

**Symptom:**
```
[ERROR] Main class not found: com.balazsholczer.pattern.App
```

**Solution:**
```bash
# Check exec.mainClass property in pom.xml
grep "exec.mainClass" pom.xml

# Verify class exists
ls src/main/java/com/balazsholczer/pattern/App.java

# Rebuild
mvn clean compile
mvn exec:java
```

#### Issue 5: Pattern Matching Features Not Recognized

**Symptom:**
```
[ERROR] pattern matching in switch is a preview feature and is disabled by default.
```

**Solution:**
```bash
# Ensure Java 21+ is used (pattern matching is standard in 21+)
java -version

# If using Java 17-20, enable preview features in POM:
# <compilerArgs>
#     <arg>--enable-preview</arg>
# </compilerArgs>
```

### Debugging Tips

```bash
# Run with debug output
mvn clean compile -X

# Run single test with debug
mvn test -Dtest=StrategyTest -X

# Skip tests during compile
mvn clean compile -DskipTests

# Run with specific log level
mvn clean test -Dorg.slf4j.simpleLogger.defaultLogLevel=debug

# Check dependency tree
mvn dependency:tree

# Verify module structure
mvn help:effective-pom
```

---

## Best Practices for AI Assistants

### When Adding Code

1. **Follow existing patterns** - Look at similar modules for conventions
2. **Implement multiple approaches** - Traditional, functional, enum-based, etc.
3. **Write comprehensive tests** - Test each approach and verify equivalence
4. **Use modern Java features** - Records, sealed classes, pattern matching where appropriate
5. **Maintain consistency** - Package names, class names, file structure
6. **Document thoroughly** - Clear comments explaining pattern intent

### When Modifying Code

1. **Run tests first** - Establish baseline before changes
2. **Maintain backward compatibility** - Don't break existing implementations
3. **Update all variants** - If changing interface, update all implementations
4. **Verify equivalence** - Ensure all approaches still produce same results
5. **Update documentation** - Keep README.md and this file current

### When Troubleshooting

1. **Start with tests** - Run `mvn test` to identify failures
2. **Isolate the issue** - Test single module with `mvn test -pl ModuleName`
3. **Check Java version** - Verify Java 21+ for modern features
4. **Verify dependencies** - Run `mvn clean install` to rebuild all
5. **Consult examples** - Look at working patterns for reference

### Communication

When reporting work to users:
- **Be specific** - Name files, line numbers, exact changes made
- **Show results** - Include test output, compilation success
- **Explain approach** - Why you chose a particular implementation
- **Document trade-offs** - Performance, readability, maintainability considerations

---

## Quick Reference

### Essential Commands
```bash
# Build everything
mvn clean compile

# Run all patterns
mvn clean compile -Prun-all-patterns

# Test everything
mvn clean test -Ptest-all-patterns

# Run single pattern
cd PatternName && mvn exec:java

# Test single pattern
cd PatternName && mvn test
```

### File Locations
- **Main class:** `src/main/java/com/balazsholczer/{pattern}/App.java`
- **Tests:** `src/test/java/com/balazsholczer/{pattern}/PatternTest.java`
- **POM:** `{PatternModule}/pom.xml`

### Package Convention
`PatternNamePattern/` → `com.balazsholczer.{pattern-lowercase}`

### Main Class Convention
Always `App.java` in pattern package, specified in `${exec.mainClass}` property.

---

**End of CLAUDE.md**

This guide is a living document. Update it when:
- Adding new patterns or categories
- Changing build conventions
- Updating Java version
- Adding new implementation approaches
- Discovering new best practices

For questions or issues, consult the README.md or examine existing pattern implementations.
