#!/bin/bash

# Cross-platform compatibility for Windows Git Bash, Pop OS, WSL Ubuntu
set -e

# Detect OS for sed compatibility
if [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "cygwin" ]]; then
    # Windows Git Bash
    SED_INPLACE="sed -i"
else
    # Linux/Unix (Pop OS, WSL Ubuntu)
    SED_INPLACE="sed -i"
fi

# Pattern to main class mapping
declare -A pattern_main_classes=(
    ["CommandPattern"]="com.balazsholczer.command.App"
    ["CommandPatternII"]="com.balazsholczer.command2.App"
    ["StatePattern"]="com.balazsholczer.state.App"
    ["TemplatePattern"]="com.balazsholczer.template.App"
    ["IteratorPattern"]="com.balazsholczer.iterator.App"
    ["VisitorPattern"]="com.balazsholczer.visitor.App"
    ["ChainOfResponsibilityPattern"]="com.balazsholczer.chainofresponsibility.App"
    ["MediatorPattern"]="com.balazsholczer.mediator.App"
    ["MementoPattern"]="com.balazsholczer.memento.App"
    ["InterpreterPattern"]="com.balazsholczer.interpreter.App"
    ["SingletonPattern"]="com.balazsholczer.singleton.App"
    ["FactoryPattern"]="com.balazsholczer.factory.App"
    ["AbstractFactoryPattern"]="com.balazsholczer.abstractfactory.App"
    ["BuilderPattern"]="com.balazsholczer.builder.App"
    ["PrototypePattern"]="com.balazsholczer.prototype.App"
    ["AdapterPattern"]="com.balazsholczer.adapter.App"
    ["AdapterPatternII"]="com.balazsholczer.adapter2.App"
    ["BridgePattern"]="com.balazsholczer.bridge.App"
    ["CompositePattern"]="com.balazsholczer.composite.App"
    ["DecoratorPattern"]="com.balazsholczer.decorator.App"
    ["FacadePattern"]="com.balazsholczer.facade.App"
    ["FlyweightPattern"]="com.balazsholczer.flyweight.App"
    ["ProxyPattern"]="com.balazsholczer.proxy.App"
    ["MVCPattern"]="com.balazsholczer.mvc.App"
    ["DAOPattern"]="com.balazsholczer.dao.App"
    ["DTOPattern"]="com.balazsholczer.dto.App"
    ["ServiceLocatorPattern"]="com.balazsholczer.servicelocator.App"
    ["NullObjectPattern"]="com.balazsholczer.nullobject.App"
    ["RepositoryPattern"]="com.balazsholczer.repository.App"
    ["UnitOfWorkPattern"]="com.balazsholczer.unitofwork.App"
    ["SpecificationPattern"]="com.balazsholczer.specification.App"
    ["BusinessDelegatePattern"]="com.balazsholczer.businessdelegate.App"
    ["APIGatewayPattern"]="com.balazsholczer.apigateway.App"
    ["SessionFacadePattern"]="com.balazsholczer.sessionfacade.App"
    ["ValueObjectPattern"]="com.balazsholczer.valueobject.App"
    ["FrontControllerPattern"]="com.balazsholczer.frontcontroller.App"
    ["DependencyInjectionPattern"]="com.balazsholczer.dependencyinjection.App"
    ["TransferObjectAssemblerPattern"]="com.balazsholczer.transferobjectassembler.App"
    ["FunctionalProgrammingPatterns"]="com.balazsholczer.functional.App"
    ["PatternMatchingPattern"]="com.balazsholczer.patternmatching.App"
)

for pattern_dir in "${!pattern_main_classes[@]}"; do
    pom_path="${pattern_dir}/pom.xml"
    if [ -f "$pom_path" ]; then
        echo "Adding exec profile to $pattern_dir..."
        
        main_class="${pattern_main_classes[$pattern_dir]}"
        
        # Check if profiles already exist
        if ! grep -q "<profiles>" "$pom_path" && grep -q "</project>" "$pom_path"; then
            # Create exec profile template
            exec_config="
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
                                    <mainClass>$main_class</mainClass>
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
                    <mainClass>$main_class</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>"
            
            # Add exec profile before closing project tag - cross-platform compatible
            $SED_INPLACE "s|</project>|$exec_config\n</project>|" "$pom_path"
        fi
    fi
done

echo "All exec profiles added!"