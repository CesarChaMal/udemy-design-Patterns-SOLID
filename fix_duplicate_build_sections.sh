#!/bin/bash

# Cross-platform compatibility for Windows Git Bash, Pop OS, WSL Ubuntu
set -e

# Create temp file function that works across platforms
create_temp_file() {
    if command -v mktemp >/dev/null 2>&1; then
        mktemp
    else
        # Fallback for systems without mktemp
        echo "/tmp/fix_build_$$_$(date +%s)"
    fi
}

# List of modules with duplicate build sections
modules_with_duplicates=(
    "RepositoryPattern" "UnitOfWorkPattern" "SpecificationPattern" 
    "BusinessDelegatePattern" "APIGatewayPattern" "SessionFacadePattern"
    "ValueObjectPattern" "FrontControllerPattern" "DependencyInjectionPattern"
    "TransferObjectAssemblerPattern" "FunctionalProgrammingPatterns" "PatternMatchingPattern"
)

for module in "${modules_with_duplicates[@]}"; do
    pom_path="${module}/pom.xml"
    if [ -f "$pom_path" ]; then
        echo "Fixing duplicate build sections in $module..."
        
        # Create a temporary file to store the fixed content - cross-platform
        temp_file=$(create_temp_file)
        
        # Use awk to remove the duplicate build section
        awk '
        BEGIN { in_duplicate_build = 0; profiles_ended = 0 }
        /<\/profiles>/ { profiles_ended = 1; print; next }
        profiles_ended && /<build>/ { in_duplicate_build = 1; next }
        in_duplicate_build && /<\/build>/ { in_duplicate_build = 0; next }
        !in_duplicate_build { print }
        ' "$pom_path" > "$temp_file"
        
        # Replace the original file
        mv "$temp_file" "$pom_path"
    fi
done

echo "All duplicate build sections fixed!"