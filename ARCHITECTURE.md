# Architecture Documentation

## 📐 System Architecture

This document describes the architecture and design of the Calculator Maven Project.

## 🏗️ High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    GitHub Repository                         │
│  ┌────────────────────────────────────────────────────┐    │
│  │              Source Code                            │    │
│  │  ┌──────────────────┐  ┌──────────────────┐       │    │
│  │  │  Calculator.java │  │ CalculatorTest   │       │    │
│  │  │                  │  │      .java       │       │    │
│  │  └──────────────────┘  └──────────────────┘       │    │
│  └────────────────────────────────────────────────────┘    │
│                           │                                  │
│                           ▼                                  │
│  ┌────────────────────────────────────────────────────┐    │
│  │          GitHub Actions CI/CD Pipeline              │    │
│  │                                                      │    │
│  │  ┌────────┐  ┌────────┐  ┌────────┐  ┌────────┐  │    │
│  │  │  Lint  │→ │ Format │→ │  Test  │→ │ Build  │  │    │
│  │  └────────┘  └────────┘  └────────┘  └────────┘  │    │
│  │                                                      │    │
│  └────────────────────────────────────────────────────┘    │
│                           │                                  │
│                           ▼                                  │
│  ┌────────────────────────────────────────────────────┐    │
│  │              Artifacts & Reports                    │    │
│  │  • Test Results  • JAR Files  • Quality Reports    │    │
│  └────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

## 📦 Component Architecture

### 1. Core Components

```
┌──────────────────────────────────────────┐
│         Calculator (Main Class)          │
├──────────────────────────────────────────┤
│ State:                                   │
│  • miResultCount: int                    │
│  • lstrLastResult: double                │
├──────────────────────────────────────────┤
│ Operations:                              │
│  • add(p1, p2): double                   │
│  • subtract(p1, p2): double              │
│  • multiply(p1, p2): double              │
│  • divide(pNum, pDen): double            │
│  • power(pBase, pExp): double            │
│  • squareRoot(pNum): double              │
├──────────────────────────────────────────┤
│ Utilities:                               │
│  • getResultCount(): int                 │
│  • getLastResult(): double               │
│  • reset(): void                         │
└──────────────────────────────────────────┘
```

### 2. Test Architecture

```
CalculatorTest (Main Test Suite)
│
├─ AdditionTests
│  ├─ Unit Tests (3)
│  └─ Parameterized Tests (4 cases)
│
├─ SubtractionTests
│  ├─ Unit Tests (2)
│  └─ Parameterized Tests (4 cases)
│
├─ MultiplicationTests
│  ├─ Unit Tests (3)
│  └─ Parameterized Tests (4 cases)
│
├─ DivisionTests
│  ├─ Unit Tests (3)
│  └─ Parameterized Tests (4 cases)
│
├─ PowerTests
│  ├─ Unit Tests (3)
│  └─ Parameterized Tests (4 cases)
│
├─ SquareRootTests
│  ├─ Unit Tests (3)
│  └─ Parameterized Tests (5 cases)
│
├─ StateTests
│  └─ Unit Tests (3)
│
└─ IntegrationTests
   └─ Complex Scenarios (2)
```

## 🔄 CI/CD Pipeline Architecture

### Pipeline Flow

```
┌─────────────────────────────────────────────────────────┐
│                    Trigger Events                        │
│  • Push to main/develop                                  │
│  • Pull Request                                          │
│  • Manual Dispatch                                       │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
        ┌─────────────────┐
        │  Job 1: LINT    │
        │  (Checkstyle)   │
        └────────┬────────┘
                 │
                 │ (Parallel)
                 ├──────────────────┐
                 ▼                  ▼
    ┌────────────────────┐  ┌──────────────┐
    │  Job 2: FORMAT     │  │   Upload     │
    │      CHECK         │  │  Artifacts   │
    └─────────┬──────────┘  └──────────────┘
              │
              │ (Both jobs must pass)
              ▼
    ┌─────────────────────┐
    │   Job 3: TEST       │
    │   (JUnit 5)         │
    └──────────┬──────────┘
               │
               ├──────────────────┐
               ▼                  ▼
    ┌──────────────────┐  ┌──────────────┐
    │  Generate        │  │   Upload     │
    │  Reports         │  │  Test Data   │
    └──────────────────┘  └──────────────┘
               │
               ▼
    ┌─────────────────────┐
    │   Job 4: BUILD      │
    │   (Package JAR)     │
    └──────────┬──────────┘
               │
               ├──────────────────┐
               ▼                  ▼
    ┌──────────────────┐  ┌──────────────┐
    │  Create JAR      │  │   Upload     │
    │  Artifact        │  │  Artifact    │
    └──────────────────┘  └──────────────┘
               │
               ▼
    ┌─────────────────────┐
    │ Job 5: QUALITY      │
    │     REPORT          │
    │   (Always runs)     │
    └─────────────────────┘
```

### Job Dependencies

```
lint ────────┐
             ├──→ test ──→ build ──→ code-quality
format-check─┘                   ↗
                                │
          (Always runs) ────────┘
```

## 🎯 Design Patterns

### 1. State Pattern
The Calculator maintains internal state:
- Tracks number of operations performed
- Stores last calculated result
- Can be reset to initial state

### 2. Template Method Pattern (Testing)
Test structure follows a template:
- Setup (@BeforeEach)
- Execute operation
- Assert result
- Verify state

### 3. Builder Pattern (Maven)
Maven builds the project incrementally:
- Compile
- Test
- Package
- Verify

## 🔐 Quality Gates

### Gate 1: Checkstyle (Lint)
```
Source Code
    │
    ▼
┌──────────────────┐
│  Checkstyle      │
│  Validation      │
└────┬─────────────┘
     │
     ├─ Naming conventions
     ├─ Import rules
     ├─ Whitespace rules
     ├─ Code structure
     │
     ▼
  Pass/Fail
```

### Gate 2: Format Check
```
Source Code
    │
    ▼
┌──────────────────┐
│  Formatter       │
│  Validation      │
└────┬─────────────┘
     │
     ├─ Indentation (4 spaces)
     ├─ Line length (120 chars)
     ├─ Brace placement
     ├─ Spacing rules
     │
     ▼
  Pass/Fail
```

### Gate 3: Tests
```
Test Suite
    │
    ▼
┌──────────────────┐
│  JUnit 5         │
│  Execution       │
└────┬─────────────┘
     │
     ├─ Unit tests
     ├─ Parameterized tests
     ├─ Exception tests
     ├─ Integration tests
     │
     ▼
┌──────────────────┐
│  Test Report     │
│  Generation      │
└──────────────────┘
     │
     ▼
  Pass/Fail
```

### Gate 4: Build
```
Compiled Classes
    │
    ▼
┌──────────────────┐
│  Maven Package   │
└────┬─────────────┘
     │
     ├─ Compile sources
     ├─ Process resources
     ├─ Create JAR
     ├─ Attach metadata
     │
     ▼
┌──────────────────┐
│  calculator-     │
│  1.0-SNAPSHOT    │
│  .jar            │
└──────────────────┘
```

## 📊 Data Flow

### Calculation Flow
```
User Input
    │
    ▼
┌────────────────────┐
│  Operation Method  │
│  (add, subtract..) │
└─────────┬──────────┘
          │
          ├─ Validate inputs
          ├─ Perform calculation
          │
          ▼
┌────────────────────┐
│  updateResult()    │
└─────────┬──────────┘
          │
          ├─ Update lstrLastResult
          ├─ Increment miResultCount
          │
          ▼
┌────────────────────┐
│  Return Result     │
└────────────────────┘
```

### Test Execution Flow
```
Test Method
    │
    ▼
┌────────────────────┐
│  @BeforeEach       │
│  setUp()           │
└─────────┬──────────┘
          │
          ├─ Create new Calculator
          │
          ▼
┌────────────────────┐
│  Test Logic        │
└─────────┬──────────┘
          │
          ├─ Call calculator method
          ├─ Capture result
          │
          ▼
┌────────────────────┐
│  Assertions        │
└─────────┬──────────┘
          │
          ├─ assertEquals()
          ├─ assertThrows()
          ├─ assertNotNull()
          │
          ▼
    Pass or Fail
```

## 🗂️ File Organization

### Source Directory Structure
```
src/
├── main/
│   └── java/
│       └── com/example/calculator/
│           └── Calculator.java           (143 lines)
│
└── test/
    └── java/
        └── com/example/calculator/
            └── CalculatorTest.java       (285 lines)
```

### Configuration Files
```
Root Directory
├── pom.xml                    (Maven config - 108 lines)
├── checkstyle.xml            (Lint rules - 82 lines)
├── formatter-config.xml      (Format rules - 22 lines)
└── .gitignore                (Git exclusions)
```

### Documentation Files
```
Root Directory
├── README.md                 (Main docs - 420 lines)
├── QUICK_START.md           (Quick guide - 240 lines)
├── PROJECT_SUMMARY.md       (Overview - 380 lines)
├── ARCHITECTURE.md          (This file)
└── CONTRIBUTING.md          (Contribution guide - 350 lines)
```

## 🔧 Technology Stack

```
┌─────────────────────────────────────────┐
│         Application Layer                │
│  ┌────────────────────────────────┐    │
│  │      Calculator.java            │    │
│  │   (Java 11 - Business Logic)    │    │
│  └────────────────────────────────┘    │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│          Testing Layer                   │
│  ┌────────────────────────────────┐    │
│  │     CalculatorTest.java         │    │
│  │   (JUnit 5 - Test Cases)        │    │
│  └────────────────────────────────┘    │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│        Build & Quality Layer             │
│  ┌─────────┐ ┌──────────┐ ┌─────────┐ │
│  │  Maven  │ │Checkstyle│ │Formatter│ │
│  │  3.9+   │ │  10.12   │ │  2.23   │ │
│  └─────────┘ └──────────┘ └─────────┘ │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│          CI/CD Layer                     │
│  ┌────────────────────────────────┐    │
│  │     GitHub Actions              │    │
│  │   (ubuntu-latest, JDK 11)       │    │
│  └────────────────────────────────┘    │
└─────────────────────────────────────────┘
```

## 🎯 Key Design Decisions

### 1. Why Custom Naming Conventions?

**Decision**: Use prefixes like `mstr`, `mi`, `mb`, `p` for variables

**Rationale**:
- Demonstrates Checkstyle customization capabilities
- Shows how to enforce team-specific conventions
- Makes variable scope immediately visible
- Educational value for understanding code standards

### 2. Why JUnit 5 Over JUnit 4?

**Decision**: Use JUnit 5 Jupiter API

**Rationale**:
- Modern testing framework
- Better parameterized test support
- Nested test organization
- Display names for better reporting
- Lambda support for exception testing

### 3. Why Separate Lint and Format Jobs?

**Decision**: Run lint and format checks as separate parallel jobs

**Rationale**:
- Faster feedback (parallel execution)
- Clear separation of concerns
- Independent failure reporting
- Easier to debug specific issues

### 4. Why Track Calculator State?

**Decision**: Add `miResultCount` and `lstrLastResult` members

**Rationale**:
- Demonstrates state management
- Enables more complex testing scenarios
- Shows real-world calculator behavior
- Provides learning opportunities

## 📈 Scalability Considerations

### Adding New Operations

```java
// 1. Add method to Calculator.java
public double modulo(double pDividend, double pDivisor) {
    if (pDivisor == 0) {
        throw new ArithmeticException("Cannot modulo by zero");
    }
    double result = pDividend % pDivisor;
    updateResult(result);
    return result;
}

// 2. Add nested test class
@Nested
@DisplayName("Modulo Tests")
class ModuloTests {
    @Test
    void testModuloBasic() {
        assertEquals(1.0, calculator.modulo(10.0, 3.0), 0.0001);
    }
}
```

### Adding New Quality Checks

```xml
<!-- Add to pom.xml -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## 🔍 Monitoring & Observability

### What Gets Tracked?

1. **Build Status**: Success/Failure for each job
2. **Test Results**: Pass/Fail count, execution time
3. **Code Quality**: Checkstyle violations, format issues
4. **Artifacts**: Generated JARs, reports
5. **History**: Trend over time in GitHub

### Where to Find Information?

- **GitHub Actions Tab**: Real-time pipeline status
- **Pull Requests**: Check results on PRs
- **Artifacts**: Downloadable reports and JARs
- **Summary**: Job summary with key metrics

## 🎓 Learning Path

For someone new to the project:

1. **Read README.md**: Understand project purpose
2. **Follow QUICK_START.md**: Get it running locally
3. **Read Calculator.java**: Understand business logic
4. **Read CalculatorTest.java**: See testing patterns
5. **Study ci-cd.yml**: Learn CI/CD workflow
6. **Review PROJECT_SUMMARY.md**: Get complete overview
7. **Read ARCHITECTURE.md**: Understand design (this file)
8. **Read CONTRIBUTING.md**: Learn how to contribute

---

**Last Updated**: 2025-11-07
**Version**: 1.0
**Status**: Production Ready

