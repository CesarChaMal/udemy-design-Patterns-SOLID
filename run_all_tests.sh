#!/bin/bash

echo "=== Running All Pattern Tests ==="
echo

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counters
total_modules=0
passed_modules=0
failed_modules=0

# Function to run tests for a module
run_module_tests() {
    local module_dir="$1"
    
    if [ -f "$module_dir/pom.xml" ] && [ -d "$module_dir/src/test" ]; then
        echo "Running tests for $module_dir..."
        cd "$module_dir" || return
        
        if mvn test -q > /dev/null 2>&1; then
            echo -e "  ${GREEN}✓${NC} $module_dir tests passed"
            passed_modules=$((passed_modules + 1))
        else
            echo -e "  ${RED}✗${NC} $module_dir tests failed"
            failed_modules=$((failed_modules + 1))
        fi
        
        cd .. || return
        total_modules=$((total_modules + 1))
    else
        echo -e "  ${YELLOW}-${NC} $module_dir skipped (no tests or POM)"
    fi
}

# Run tests for all modules
for dir in */; do
    if [ -d "$dir" ] && [ "$dir" != "target/" ] && [ -d "$dir/src/" ]; then
        module_name=$(basename "$dir")
        run_module_tests "$module_name"
    fi
done

echo
echo "=== Test Results Summary ==="
echo -e "Total modules tested: ${YELLOW}$total_modules${NC}"
echo -e "Passed: ${GREEN}$passed_modules${NC}"
echo -e "Failed: ${RED}$failed_modules${NC}"

if [ "$failed_modules" -eq 0 ]; then
    echo -e "${GREEN}All tests passed!${NC}"
    exit 0
else
    echo -e "${RED}Some tests failed.${NC}"
    exit 1
fi