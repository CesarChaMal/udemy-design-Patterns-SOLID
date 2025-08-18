import os
import re

# Pattern to main class mapping
pattern_main_classes = {
    'CommandPattern': 'com.balazsholczer.command.App',
    'CommandPatternII': 'com.balazsholczer.command2.App',
    'StatePattern': 'com.balazsholczer.state.App',
    'TemplatePattern': 'com.balazsholczer.template.App',
    'IteratorPattern': 'com.balazsholczer.iterator.App',
    'VisitorPattern': 'com.balazsholczer.visitor.App',
    'ChainOfResponsibilityPattern': 'com.balazsholczer.chainofresponsibility.App',
    'MediatorPattern': 'com.balazsholczer.mediator.App',
    'MementoPattern': 'com.balazsholczer.memento.App',
    'InterpreterPattern': 'com.balazsholczer.interpreter.App',
    'SingletonPattern': 'com.balazsholczer.singleton.App',
    'FactoryPattern': 'com.balazsholczer.factory.App',
    'AbstractFactoryPattern': 'com.balazsholczer.abstractfactory.App',
    'BuilderPattern': 'com.balazsholczer.builder.App',
    'PrototypePattern': 'com.balazsholczer.prototype.App',
    'AdapterPattern': 'com.balazsholczer.adapter.App',
    'AdapterPatternII': 'com.balazsholczer.adapter2.App',
    'BridgePattern': 'com.balazsholczer.bridge.App',
    'CompositePattern': 'com.balazsholczer.composite.App',
    'DecoratorPattern': 'com.balazsholczer.decorator.App',
    'FacadePattern': 'com.balazsholczer.facade.App',
    'FlyweightPattern': 'com.balazsholczer.flyweight.App',
    'ProxyPattern': 'com.balazsholczer.proxy.App',
    'MVCPattern': 'com.balazsholczer.mvc.App',
    'DAOPattern': 'com.balazsholczer.dao.App',
    'DTOPattern': 'com.balazsholczer.dto.App',
    'ServiceLocatorPattern': 'com.balazsholczer.servicelocator.App',
    'NullObjectPattern': 'com.balazsholczer.nullobject.App',
    'RepositoryPattern': 'com.balazsholczer.repository.App',
    'UnitOfWorkPattern': 'com.balazsholczer.unitofwork.App',
    'SpecificationPattern': 'com.balazsholczer.specification.App',
    'BusinessDelegatePattern': 'com.balazsholczer.businessdelegate.App',
    'APIGatewayPattern': 'com.balazsholczer.apigateway.App',
    'SessionFacadePattern': 'com.balazsholczer.sessionfacade.App',
    'ValueObjectPattern': 'com.balazsholczer.valueobject.App',
    'FrontControllerPattern': 'com.balazsholczer.frontcontroller.App',
    'DependencyInjectionPattern': 'com.balazsholczer.dependencyinjection.App',
    'TransferObjectAssemblerPattern': 'com.balazsholczer.transferobjectassembler.App',
    'FunctionalProgrammingPatterns': 'com.balazsholczer.functional.App',
    'PatternMatchingPattern': 'com.balazsholczer.patternmatching.App'
}

exec_profile_template = '''
    <profiles>
        <profile>
            <id>run-all-patterns</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>exec-maven-plugin</artifactId>
                        <version>3.1.0</version>
                        <executions>
                            <execution>
                                <phase>compile</phase>
                                <goals>
                                    <goal>java</goal>
                                </goals>
                                <configuration>
                                    <mainClass>{main_class}</mainClass>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>{main_class}</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>'''

for pattern_dir, main_class in pattern_main_classes.items():
    pom_path = f"{pattern_dir}/pom.xml"
    if os.path.exists(pom_path):
        print(f"Adding exec profile to {pattern_dir}...")
        
        with open(pom_path, 'r') as f:
            content = f.read()
        
        # Check if profiles already exist
        if '<profiles>' not in content and '</project>' in content:
            # Add exec profile before closing project tag
            exec_config = exec_profile_template.format(main_class=main_class)
            content = content.replace('</project>', exec_config + '\n</project>')
            
            with open(pom_path, 'w') as f:
                f.write(content)

print("All exec profiles added!")