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

echo "Fixing all pattern module POMs..."

for dir in *Pattern; do
    if [ -d "$dir" ] && [ -f "$dir/pom.xml" ]; then
        echo "Fixing $dir..."
        $SED_INPLACE 's/ultimate-design-patterns/udemy-design-patterns-solid/g' "$dir/pom.xml"
        $SED_INPLACE 's/1\.0\.0/1.0-SNAPSHOT/g' "$dir/pom.xml"
    fi
done

echo "All pattern POMs fixed!"