#!/bin/bash

echo "=========================================="
echo "Calculator Maven Project - Setup & Test"
echo "=========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null
then
    echo "❌ Java is not installed. Please install Java JDK 11 or higher."
    exit 1
fi

echo "✅ Maven version:"
mvn -version | head -1
echo ""

echo "✅ Java version:"
java -version 2>&1 | head -1
echo ""

echo "=========================================="
echo "Step 1: Clean and Compile"
echo "=========================================="
mvn clean compile
if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi
echo "✅ Compilation successful!"
echo ""

echo "=========================================="
echo "Step 2: Run Checkstyle (Lint)"
echo "=========================================="
mvn checkstyle:check
if [ $? -ne 0 ]; then
    echo "❌ Checkstyle validation failed!"
    echo "Please fix naming convention issues and try again."
    exit 1
fi
echo "✅ Checkstyle validation passed!"
echo ""

echo "=========================================="
echo "Step 3: Validate Code Formatting"
echo "=========================================="
mvn formatter:validate
if [ $? -ne 0 ]; then
    echo "⚠️  Code formatting validation failed!"
    echo "Running formatter to fix..."
    mvn formatter:format
    echo "✅ Code formatted successfully!"
else
    echo "✅ Code formatting is correct!"
fi
echo ""

echo "=========================================="
echo "Step 4: Run Tests"
echo "=========================================="
mvn test
if [ $? -ne 0 ]; then
    echo "❌ Tests failed!"
    exit 1
fi
echo "✅ All tests passed!"
echo ""

echo "=========================================="
echo "Step 5: Build Package"
echo "=========================================="
mvn package -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Build failed!"
    exit 1
fi
echo "✅ Build successful!"
echo ""

echo "=========================================="
echo "✅ ALL CHECKS PASSED!"
echo "=========================================="
echo ""
echo "📦 Generated artifacts:"
ls -lh target/*.jar 2>/dev/null || echo "No JAR files found"
echo ""
echo "📊 Test reports available at:"
echo "   - target/surefire-reports/"
echo ""
echo "🎉 Project is ready for GitHub!"
echo ""

