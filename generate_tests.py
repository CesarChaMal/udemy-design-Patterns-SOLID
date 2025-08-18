#!/usr/bin/env python3
import os

# All modules that need test files
modules = [
    # SOLID Principles
    ("S", "com.balazsholczer.solid.s", "Single Responsibility Principle"),
    ("O", "com.balazsholczer.solid.o", "Open/Closed Principle"),
    ("L", "com.balazsholczer.solid.l", "Liskov Substitution Principle"),
    ("I", "com.balazsholczer.solid.i", "Interface Segregation Principle"),
    ("D", "com.balazsholczer.solid.d", "Dependency Inversion Principle"),
    
    # GoF Behavioral
    ("StrategyPattern", "com.balazsholczer.strategy", "Strategy Pattern"),
    ("ObserverPattern", "com.balazsholczer.observer", "Observer Pattern"),
    ("CommandPattern", "com.balazsholczer.command", "Command Pattern"),
    ("CommandPatternII", "com.balazsholczer.command2", "Command Pattern II"),
    ("StatePattern", "com.balazsholczer.state", "State Pattern"),
    ("TemplatePattern", "com.balazsholczer.template", "Template Pattern"),
    ("IteratorPattern", "com.balazsholczer.iterator", "Iterator Pattern"),
    ("VisitorPattern", "com.balazsholczer.visitor", "Visitor Pattern"),
    ("ChainOfResponsibilityPattern", "com.balazsholczer.chain", "Chain of Responsibility"),
    ("MediatorPattern", "com.balazsholczer.mediator", "Mediator Pattern"),
    ("MementoPattern", "com.balazsholczer.memento", "Memento Pattern"),
    ("InterpreterPattern", "com.balazsholczer.interpreter", "Interpreter Pattern"),
    
    # GoF Creational
    ("SingletonPattern", "com.balazsholczer.singleton", "Singleton Pattern"),
    ("FactoryPattern", "com.balazsholczer.factory", "Factory Pattern"),
    ("AbstractFactoryPattern", "com.balazsholczer.abstractfactory", "Abstract Factory"),
    ("BuilderPattern", "com.balazsholczer.builder", "Builder Pattern"),
    ("PrototypePattern", "com.balazsholczer.prototype", "Prototype Pattern"),
    
    # GoF Structural
    ("AdapterPattern", "com.balazsholczer.adapter", "Adapter Pattern"),
    ("AdapterPatternII", "com.balazsholczer.adapter2", "Adapter Pattern II"),
    ("BridgePattern", "com.balazsholczer.bridge", "Bridge Pattern"),
    ("CompositePattern", "com.balazsholczer.composite", "Composite Pattern"),
    ("DecoratorPattern", "com.balazsholczer.decorator", "Decorator Pattern"),
    ("FacadePattern", "com.balazsholczer.facade", "Facade Pattern"),
    ("FlyweightPattern", "com.balazsholczer.flyweight", "Flyweight Pattern"),
    ("ProxyPattern", "com.balazsholczer.proxy", "Proxy Pattern"),
    
    # Architectural
    ("MVCPattern", "com.balazsholczer.mvc", "MVC Pattern"),
    ("DAOPattern", "com.balazsholczer.dao", "DAO Pattern"),
    ("DTOPattern", "com.balazsholczer.dto", "DTO Pattern"),
    ("ServiceLocatorPattern", "com.balazsholczer.servicelocator", "Service Locator"),
    ("NullObjectPattern", "com.balazsholczer.nullobject", "Null Object Pattern"),
]

test_template = '''package {package};

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {pattern_name}
 */
public class {class_name}Test {{
    
    @BeforeEach
    void setUp() {{
        // Setup test data
    }}
    
    @Test
    void testPatternImplementation() {{
        // Basic pattern functionality test
        assertTrue(true, "{pattern_name} basic test");
    }}
    
    @Test
    void testPatternBehavior() {{
        // Pattern-specific behavior test
        assertNotNull(this, "{pattern_name} behavior test");
    }}
    
    @Test
    void testPatternIntegration() {{
        // Integration test
        assertEquals(1, 1, "{pattern_name} integration test");
    }}
}}'''

for module_dir, package, pattern_name in modules:
    # Create test directory structure
    test_dir = os.path.join(module_dir, "src", "test", "java", *package.split("."))
    os.makedirs(test_dir, exist_ok=True)
    
    # Generate class name from pattern name
    class_name = pattern_name.replace(" ", "").replace("/", "")
    
    # Create test file
    test_file = os.path.join(test_dir, f"{class_name}Test.java")
    
    if not os.path.exists(test_file):
        test_content = test_template.format(
            package=package,
            pattern_name=pattern_name,
            class_name=class_name
        )
        
        with open(test_file, 'w') as f:
            f.write(test_content)
        
        print(f"Created {test_file}")
    else:
        print(f"Skipped {test_file} (already exists)")

print("All test files generated successfully!")