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

# List of pattern directories that need fixing
pattern_dirs=(
    "CommandPattern" "CommandPatternII" "StatePattern" "TemplatePattern" 
    "IteratorPattern" "VisitorPattern" "ChainOfResponsibilityPattern" 
    "MediatorPattern" "MementoPattern" "InterpreterPattern" "SingletonPattern"
    "FactoryPattern" "AbstractFactoryPattern" "BuilderPattern" "PrototypePattern"
    "AdapterPattern" "AdapterPatternII" "BridgePattern" "CompositePattern"
    "DecoratorPattern" "FacadePattern" "FlyweightPattern" "ProxyPattern"
    "MVCPattern" "DAOPattern" "DTOPattern" "ServiceLocatorPattern" 
    "NullObjectPattern" "RepositoryPattern" "UnitOfWorkPattern" 
    "SpecificationPattern" "BusinessDelegatePattern" "APIGatewayPattern"
    "SessionFacadePattern" "ValueObjectPattern" "FrontControllerPattern"
    "DependencyInjectionPattern" "TransferObjectAssemblerPattern"
    "FunctionalProgrammingPatterns" "PatternMatchingPattern"
)

for pattern_dir in "${pattern_dirs[@]}"; do
    pom_path="${pattern_dir}/pom.xml"
    if [ -f "$pom_path" ]; then
        echo "Fixing $pattern_dir..."
        
        # Fix parent reference - cross-platform compatible
        $SED_INPLACE 's/ultimate-design-patterns/udemy-design-patterns-solid/g' "$pom_path"
        $SED_INPLACE 's/<version>1\.0\.0<\/version>/<version>1.0-SNAPSHOT<\/version>/g' "$pom_path"
    fi
done

echo "All pattern POMs fixed!"