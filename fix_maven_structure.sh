#!/bin/bash

echo "=== Fixing Maven Directory Structure for All Modules ==="
echo

# Function to fix directory structure
fix_module() {
    local module="$1"
    if [ -d "$module/src/com" ]; then
        echo "Fixing $module..."
        mkdir -p "$module/src/main/java" 2>/dev/null
        if cp -r "$module/src/com" "$module/src/main/java/" 2>/dev/null; then
            rm -rf "$module/src/com"
            echo "  ✓ Fixed $module"
        else
            echo "  ✗ Failed to fix $module"
        fi
    else
        echo "  - $module already has correct structure"
    fi
}

# Fix all pattern modules and SOLID principles
for module in *Pattern* S O L I D; do
    if [ -d "$module" ]; then
        fix_module "$module"
    fi
done

echo
echo "=== Maven Structure Fix Complete ==="