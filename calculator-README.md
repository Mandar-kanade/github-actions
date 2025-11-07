# Calculator Maven Project

A sample Java Maven calculator project demonstrating CI/CD practices with GitHub Actions, JUnit 5 testing, and custom code quality standards.

## 🎯 Project Overview

This project is designed to showcase:
- **Java Maven Project Structure**: Standard Maven project layout
- **JUnit 5 Testing**: Comprehensive test suite with nested tests and parameterized tests
- **GitHub Actions CI/CD**: Automated pipeline for linting, formatting, testing, and building
- **Custom Coding Standards**: Enforced via Checkstyle with custom naming conventions
- **Code Formatting**: Automated code formatting checks

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Code Quality](#code-quality)
- [CI/CD Pipeline](#cicd-pipeline)
- [Coding Conventions](#coding-conventions)
- [Maven Commands](#maven-commands)

## ✨ Features

### Calculator Operations
- Addition
- Subtraction
- Multiplication
- Division (with zero-division handling)
- Power calculation
- Square root (with negative number handling)
- Operation tracking and state management

### Testing
- 35+ JUnit 5 test cases
- Nested test classes for organized test structure
- Parameterized tests for comprehensive coverage
- Exception testing
- Integration tests

## 📁 Project Structure

```
github-actions/                       # Project root
├── .github/
│   └── workflows/
│       └── ci-cd.yml                 # GitHub Actions workflow
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/calculator/
│   │           └── Calculator.java   # Main calculator class
│   └── test/
│       └── java/
│           └── com/example/calculator/
│               └── CalculatorTest.java # JUnit 5 tests
├── checkstyle.xml                    # Checkstyle configuration
├── formatter-config.xml              # Code formatter configuration
├── pom.xml                           # Maven configuration
├── .gitignore                        # Git ignore rules
├── README.md                         # This file
├── QUICK_START.md                    # Quick start guide
├── ARCHITECTURE.md                   # Architecture documentation
├── CONTRIBUTING.md                   # Contributing guidelines
├── PROJECT_SUMMARY.md                # Project summary
├── setup-and-test.bat                # Windows setup script
└── setup-and-test.sh                 # Linux/Mac setup script
```

## 🔧 Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: 3.6.0 or higher
- **Git**: For version control

### Verify Installation

```bash
java -version
mvn -version
git --version
```

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd github-actions
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Tests

```bash
mvn test
```

## 🧪 Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=CalculatorTest
```

### Run Specific Test Method

```bash
mvn test -Dtest=CalculatorTest#testAddPositiveNumbers
```

### Generate Test Report

```bash
mvn surefire-report:report
```

The report will be available at: `target/site/surefire-report.html`

## 🎨 Code Quality

### Checkstyle Validation

Run Checkstyle to validate code against custom naming conventions:

```bash
mvn checkstyle:check
```

### Code Formatting

#### Check Formatting

```bash
mvn formatter:validate
```

#### Auto-Format Code

```bash
mvn formatter:format
```

## 🔄 CI/CD Pipeline

The GitHub Actions workflow (`ci-cd.yml`) includes four main jobs:

### 1. **Lint Job**
- Runs Checkstyle validation
- Enforces custom naming conventions
- Uploads checkstyle reports as artifacts

### 2. **Format Check Job**
- Validates code formatting
- Ensures consistent code style
- Runs independently of lint job

### 3. **Test Job**
- Executes all JUnit 5 tests
- Generates test reports
- Uploads test results as artifacts
- Publishes test report summaries
- Only runs if lint and format checks pass

### 4. **Build Job**
- Compiles the project
- Packages into JAR file
- Uploads build artifacts
- Only runs if all tests pass

### 5. **Code Quality Report Job**
- Aggregates results from all jobs
- Generates summary report
- Runs regardless of other job outcomes

### Pipeline Trigger Events

The pipeline runs on:
- Push to `main` or `develop` branches
- Pull requests to `main` or `develop` branches
- Manual workflow dispatch

## 📏 Coding Conventions

This project enforces strict naming conventions via Checkstyle:

### Member Variables
Must start with one of these prefixes:
- `mstr` - Member string variables (e.g., `mstrName`)
- `mi` - Member integer variables (e.g., `miCount`)
- `mb` - Member boolean variables (e.g., `mbActive`)
- `lstr` - Local string variables (e.g., `lstrLastResult`)
- `li` - Local integer variables (e.g., `liIndex`)
- `lb` - Local boolean variables (e.g., `lbValid`)

**Example:**
```java
private int miResultCount;
private double lstrLastResult;
private boolean mbEnabled;
```

### Parameter Names
Must start with lowercase `p` followed by uppercase letter:
- `pFirstNumber`
- `pSecondNumber`
- `pResult`

**Example:**
```java
public double add(double pFirstNumber, double pSecondNumber) {
    // implementation
}
```

### Method Names
- Lowercase first letter
- CamelCase for subsequent words
- Example: `calculateSum`, `getResult`, `isValid`

### Class Names
- Uppercase first letter
- CamelCase
- Example: `Calculator`, `CalculatorTest`

## 🛠 Maven Commands

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Package without tests
mvn clean package -DskipTests

# Install to local repository
mvn clean install
```

### Testing Commands

```bash
# Run tests
mvn test

# Run tests with coverage
mvn clean test jacoco:report

# Skip tests during build
mvn clean install -DskipTests
```

### Code Quality Commands

```bash
# Run checkstyle
mvn checkstyle:check

# Generate checkstyle report
mvn checkstyle:checkstyle

# Validate formatting
mvn formatter:validate

# Format code
mvn formatter:format
```

### Reporting Commands

```bash
# Generate all reports
mvn site

# Generate test report only
mvn surefire-report:report
```

## 📊 Test Coverage

The test suite includes:
- **Addition Tests**: 4 tests (including parameterized)
- **Subtraction Tests**: 3 tests (including parameterized)
- **Multiplication Tests**: 4 tests (including parameterized)
- **Division Tests**: 4 tests (including parameterized)
- **Power Tests**: 4 tests (including parameterized)
- **Square Root Tests**: 4 tests (including parameterized)
- **State Management Tests**: 3 tests
- **Integration Tests**: 2 tests

**Total: 28+ test cases** (more with parameterized test variations)

## 🐛 Troubleshooting

### Checkstyle Failures

If Checkstyle validation fails:
1. Check the error message for specific naming violations
2. Ensure member variables start with `mstr`, `mi`, `mb`, `lstr`, `li`, or `lb`
3. Ensure parameters start with `p`

### Format Check Failures

If format validation fails:
1. Run `mvn formatter:format` locally
2. Commit the formatted code
3. Push again

### Test Failures

If tests fail:
1. Check test output in console
2. Review test reports at `target/surefire-reports/`
3. Ensure Calculator implementation matches test expectations

## 📝 Contributing

When contributing to this project:
1. Follow the naming conventions
2. Write tests for new features
3. Ensure all tests pass locally
4. Run formatter before committing
5. Validate checkstyle compliance

## 📄 License

This is a sample project for educational purposes.

## 🤝 Support

For issues or questions:
1. Check existing GitHub Issues
2. Review the troubleshooting section
3. Create a new issue with detailed information

## 🎓 Learning Resources

- [Maven Documentation](https://maven.apache.org/guides/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Checkstyle Documentation](https://checkstyle.org/)

---

**Happy Coding! 🚀**

