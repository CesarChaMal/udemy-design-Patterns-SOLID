#!/bin/bash

echo "=== Building All Pattern Modules ==="
echo

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Build root project first
echo "Building root project..."
if mvn clean compile -q > /dev/null 2>&1; then
    echo -e "${GREEN}✓${NC} Root project built successfully"
else
    echo -e "${RED}✗${NC} Root project build failed"
    exit 1
fi

# Counters
total_modules=0
built_modules=0
failed_modules=0

# Function to build a module
build_module() {
    local module_dir="$1"
    
    if [ -f "$module_dir/pom.xml" ]; then
        echo "Building $module_dir..."
        cd "$module_dir" || return
        
        if mvn clean compile -q > /dev/null 2>&1; then
            echo -e "  ${GREEN}✓${NC} $module_dir built successfully"
            built_modules=$((built_modules + 1))
        else
            echo -e "  ${RED}✗${NC} $module_dir build failed"
            failed_modules=$((failed_modules + 1))
        fi
        
        cd .. || return
        total_modules=$((total_modules + 1))
    else
        echo -e "  ${YELLOW}-${NC} $module_dir skipped (no POM)"
    fi
}

# Build all modules
for dir in */; do
    if [ -d "$dir" ] && [ "$dir" != "target/" ] && [ -d "$dir/src/" ]; then
        module_name=$(basename "$dir")
        build_module "$module_name"
    fi
done

echo
echo "=== Build Results Summary ==="
echo -e "Total modules: ${YELLOW}$total_modules${NC}"
echo -e "Built successfully: ${GREEN}$built_modules${NC}"
echo -e "Build failed: ${RED}$failed_modules${NC}"

if [ "$failed_modules" -eq 0 ]; then
    echo -e "${GREEN}All modules built successfully!${NC}"
    exit 0
else
    echo -e "${RED}Some modules failed to build.${NC}"
    exit 1
fi