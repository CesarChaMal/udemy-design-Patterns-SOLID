#!/usr/bin/env python3
import os
import xml.etree.ElementTree as ET

# All modules that need JUnit dependency
modules = [
    "S", "O", "L", "I", "D",
    "StrategyPattern", "ObserverPattern", "CommandPattern", "CommandPatternII",
    "StatePattern", "TemplatePattern", "IteratorPattern", "VisitorPattern",
    "ChainOfResponsibilityPattern", "MediatorPattern", "MementoPattern", "InterpreterPattern",
    "SingletonPattern", "FactoryPattern", "AbstractFactoryPattern", "BuilderPattern", "PrototypePattern",
    "AdapterPattern", "AdapterPatternII", "BridgePattern", "CompositePattern",
    "DecoratorPattern", "FacadePattern", "FlyweightPattern", "ProxyPattern",
    "MVCPattern", "DAOPattern", "DTOPattern", "ServiceLocatorPattern", "NullObjectPattern"
]

junit_dependency = '''
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>'''

for module in modules:
    pom_path = os.path.join(module, "pom.xml")
    if os.path.exists(pom_path):
        with open(pom_path, 'r') as f:
            content = f.read()
        
        # Add JUnit dependency if not present
        if '<dependencies>' not in content and 'junit' not in content:
            # Insert before closing </project> tag
            content = content.replace('</project>', junit_dependency + '\n</project>')
            
            with open(pom_path, 'w') as f:
                f.write(content)
            
            print(f"Added JUnit dependency to {pom_path}")
        else:
            print(f"Skipped {pom_path} (already has dependencies)")

print("JUnit dependencies added successfully!")