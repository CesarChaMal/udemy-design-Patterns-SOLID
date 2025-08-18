#!/usr/bin/env python3
import os

# Pattern modules that need POMs
patterns = [
    # GoF Behavioral
    ("CommandPatternII", "command-pattern-ii", "Command Pattern II", "GoF Behavioral - Advanced Command Pattern", "com.balazsholczer.command2.App"),
    ("StatePattern", "state-pattern", "State Pattern", "GoF Behavioral - State Pattern", "com.balazsholczer.state.App"),
    ("TemplatePattern", "template-pattern", "Template Pattern", "GoF Behavioral - Template Method Pattern", "com.balazsholczer.template.App"),
    ("IteratorPattern", "iterator-pattern", "Iterator Pattern", "GoF Behavioral - Iterator Pattern", "com.balazsholczer.iterator.App"),
    ("VisitorPattern", "visitor-pattern", "Visitor Pattern", "GoF Behavioral - Visitor Pattern", "com.balazsholczer.visitor.App"),
    ("ChainOfResponsibilityPattern", "chain-pattern", "Chain of Responsibility", "GoF Behavioral - Chain of Responsibility", "com.balazsholczer.chain.App"),
    ("MediatorPattern", "mediator-pattern", "Mediator Pattern", "GoF Behavioral - Mediator Pattern", "com.balazsholczer.mediator.App"),
    ("MementoPattern", "memento-pattern", "Memento Pattern", "GoF Behavioral - Memento Pattern", "com.balazsholczer.memento.App"),
    ("InterpreterPattern", "interpreter-pattern", "Interpreter Pattern", "GoF Behavioral - Interpreter Pattern", "com.balazsholczer.interpreter.App"),
    
    # GoF Creational
    ("SingletonPattern", "singleton-pattern", "Singleton Pattern", "GoF Creational - Singleton Pattern", "com.balazsholczer.singleton.App"),
    ("FactoryPattern", "factory-pattern", "Factory Pattern", "GoF Creational - Factory Method Pattern", "com.balazsholczer.factory.App"),
    ("AbstractFactoryPattern", "abstract-factory-pattern", "Abstract Factory", "GoF Creational - Abstract Factory Pattern", "com.balazsholczer.abstractfactory.App"),
    ("BuilderPattern", "builder-pattern", "Builder Pattern", "GoF Creational - Builder Pattern", "com.balazsholczer.builder.App"),
    ("PrototypePattern", "prototype-pattern", "Prototype Pattern", "GoF Creational - Prototype Pattern", "com.balazsholczer.prototype.App"),
    
    # GoF Structural
    ("AdapterPattern", "adapter-pattern", "Adapter Pattern", "GoF Structural - Adapter Pattern", "com.balazsholczer.adapter.App"),
    ("AdapterPatternII", "adapter-pattern-ii", "Adapter Pattern II", "GoF Structural - Advanced Adapter Pattern", "com.balazsholczer.adapter2.App"),
    ("BridgePattern", "bridge-pattern", "Bridge Pattern", "GoF Structural - Bridge Pattern", "com.balazsholczer.bridge.App"),
    ("CompositePattern", "composite-pattern", "Composite Pattern", "GoF Structural - Composite Pattern", "com.balazsholczer.composite.App"),
    ("DecoratorPattern", "decorator-pattern", "Decorator Pattern", "GoF Structural - Decorator Pattern", "com.balazsholczer.decorator.App"),
    ("FacadePattern", "facade-pattern", "Facade Pattern", "GoF Structural - Facade Pattern", "com.balazsholczer.facade.App"),
    ("FlyweightPattern", "flyweight-pattern", "Flyweight Pattern", "GoF Structural - Flyweight Pattern", "com.balazsholczer.flyweight.App"),
    ("ProxyPattern", "proxy-pattern", "Proxy Pattern", "GoF Structural - Proxy Pattern", "com.balazsholczer.proxy.App"),
    
    # Architectural
    ("MVCPattern", "mvc-pattern", "MVC Pattern", "Architectural - Model-View-Controller", "com.balazsholczer.mvc.App"),
    ("DAOPattern", "dao-pattern", "DAO Pattern", "Architectural - Data Access Object", "com.balazsholczer.dao.App"),
    ("DTOPattern", "dto-pattern", "DTO Pattern", "Architectural - Data Transfer Object", "com.balazsholczer.dto.App"),
    ("ServiceLocatorPattern", "service-locator-pattern", "Service Locator", "Architectural - Service Locator Pattern", "com.balazsholczer.servicelocator.App"),
    ("NullObjectPattern", "null-object-pattern", "Null Object Pattern", "Architectural - Null Object Pattern", "com.balazsholczer.nullobject.App"),
]

pom_template = '''<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.balazsholczer</groupId>
        <artifactId>ultimate-design-patterns</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>{artifact_id}</artifactId>
    <packaging>jar</packaging>

    <name>{name}</name>
    <description>{description}</description>

    <properties>
        <exec.mainClass>{main_class}</exec.mainClass>
    </properties>
</project>'''

for module_dir, artifact_id, name, description, main_class in patterns:
    pom_path = os.path.join(module_dir, "pom.xml")
    if not os.path.exists(pom_path):
        pom_content = pom_template.format(
            artifact_id=artifact_id,
            name=name,
            description=description,
            main_class=main_class
        )
        with open(pom_path, 'w') as f:
            f.write(pom_content)
        print(f"Created {pom_path}")
    else:
        print(f"Skipped {pom_path} (already exists)")

print("All POMs generated successfully!")