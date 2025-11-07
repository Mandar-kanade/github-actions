@echo off
echo ==========================================
echo Calculator Maven Project - Setup and Test
echo ==========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Maven is not installed. Please install Maven first.
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Java is not installed. Please install Java JDK 11 or higher.
    exit /b 1
)

echo Maven and Java detected
call mvn -version | findstr "Apache Maven"
call java -version 2>&1 | findstr "version"
echo.

echo ==========================================
echo Step 1: Clean and Compile
echo ==========================================
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo X Compilation failed!
    exit /b 1
)
echo Compilation successful!
echo.

echo ==========================================
echo Step 2: Run Checkstyle (Lint)
echo ==========================================
call mvn checkstyle:check
if %ERRORLEVEL% NEQ 0 (
    echo X Checkstyle validation failed!
    echo Please fix naming convention issues and try again.
    exit /b 1
)
echo Checkstyle validation passed!
echo.

echo ==========================================
echo Step 3: Validate Code Formatting
echo ==========================================
call mvn formatter:validate
if %ERRORLEVEL% NEQ 0 (
    echo Code formatting validation failed!
    echo Running formatter to fix...
    call mvn formatter:format
    echo Code formatted successfully!
) else (
    echo Code formatting is correct!
)
echo.

echo ==========================================
echo Step 4: Run Tests
echo ==========================================
call mvn test
if %ERRORLEVEL% NEQ 0 (
    echo X Tests failed!
    exit /b 1
)
echo All tests passed!
echo.

echo ==========================================
echo Step 5: Build Package
echo ==========================================
call mvn package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo X Build failed!
    exit /b 1
)
echo Build successful!
echo.

echo ==========================================
echo ALL CHECKS PASSED!
echo ==========================================
echo.
echo Generated artifacts:
dir /B target\*.jar 2>nul
echo.
echo Test reports available at:
echo    - target\surefire-reports\
echo.
echo Project is ready for GitHub!
echo.
pause

