@echo off
echo === Fixing Maven Directory Structure for All Modules ===
echo.

for /d %%i in (*Pattern* S O L I D) do (
    if exist "%%i\src\com" (
        echo Fixing %%i...
        mkdir "%%i\src\main\java" 2>nul
        xcopy "%%i\src\com" "%%i\src\main\java\com" /E /Y /Q >nul
        if %ERRORLEVEL% EQU 0 (
            rmdir /S /Q "%%i\src\com"
            echo   ✓ Fixed %%i
        ) else (
            echo   ✗ Failed to fix %%i
        )
    ) else (
        echo   - %%i already has correct structure
    )
)

echo.
echo === Maven Structure Fix Complete ===