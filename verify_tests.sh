#!/bin/bash

echo "=== Verifying All Pattern Tests ==="
echo

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counters
total_modules=0
modules_with_tests=0
modules_without_tests=0
total_test_files=0

# Function to check if module has tests
check_module_tests() {
    local module_dir="$1"
    local has_tests=false
    local test_count=0
    
    if [ -d "$module_dir/src/test" ]; then
        test_files=$(find "$module_dir/src/test" -name "*Test.java" -type f 2>/dev/null)
        test_count=$(echo "$test_files" | grep -c "Test.java" 2>/dev/null || echo "0")
        
        if [ "$test_count" -gt 0 ]; then
            has_tests=true
            modules_with_tests=$((modules_with_tests + 1))
            total_test_files=$((total_test_files + test_count))
            echo -e "  ${GREEN}✓${NC} $module_dir ($test_count test files)"
        else
            modules_without_tests=$((modules_without_tests + 1))
            echo -e "  ${RED}✗${NC} $module_dir (no test files)"
        fi
    else
        modules_without_tests=$((modules_without_tests + 1))
        echo -e "  ${RED}✗${NC} $module_dir (no test directory)"
    fi
    
    total_modules=$((total_modules + 1))
}

# Function to verify test compilation
verify_test_compilation() {
    local module_dir="$1"
    
    if [ -f "$module_dir/pom.xml" ]; then
        echo "Verifying compilation for $module_dir..."
        cd "$module_dir" || return
        
        if mvn test-compile -q > /dev/null 2>&1; then
            echo -e "  ${GREEN}✓${NC} $module_dir compiles successfully"
        else
            echo -e "  ${RED}✗${NC} $module_dir has compilation errors"
        fi
        
        cd .. || return
    fi
}

echo "Scanning all modules for tests..."
echo

# Scan all directories
for dir in */; do
    if [ -d "$dir" ] && [ "$dir" != "target/" ] && [ -d "$dir/src/" ]; then
        module_name=$(basename "$dir")
        check_module_tests "$module_name"
    fi
done

echo
echo "=== Test Coverage Summary ==="
echo -e "Total modules: ${YELLOW}$total_modules${NC}"
echo -e "Modules with tests: ${GREEN}$modules_with_tests${NC}"
echo -e "Modules without tests: ${RED}$modules_without_tests${NC}"
echo -e "Total test files: ${YELLOW}$total_test_files${NC}"

coverage_percentage=$((modules_with_tests * 100 / total_modules))
echo -e "Test coverage: ${YELLOW}$coverage_percentage%${NC}"

if [ "$modules_without_tests" -gt 0 ]; then
    echo
    echo -e "${YELLOW}Modules missing tests:${NC}"
    for dir in */; do
        if [ -d "$dir" ] && [ "$dir" != "target/" ] && [ -d "$dir/src/" ]; then
            module_name=$(basename "$dir")
            if [ ! -d "$module_name/src/test" ] || [ $(find "$module_name/src/test" -name "*Test.java" -type f 2>/dev/null | wc -l) -eq 0 ]; then
                echo "  - $module_name"
            fi
        fi
    done
fi

echo
echo "=== Verification Complete ==="