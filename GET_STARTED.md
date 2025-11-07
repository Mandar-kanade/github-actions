# 🚀 Get Started with Calculator CI/CD Project

Welcome! This guide will help you get started with the Calculator Maven Project in just a few minutes.

## 📍 You Are Here

```
github-actions/                        ← YOU ARE HERE! Your project root
├── README.md                          ← Main documentation
├── GET_STARTED.md                     ← This file
├── QUICK_START.md                     ← Quick start guide
├── .github/workflows/ci-cd.yml        ← GitHub Actions pipeline
├── src/                               ← Source code
├── pom.xml                            ← Maven configuration
├── checkstyle.xml                     ← Code quality rules
└── Documentation files...
```

## ⚡ Quick Start (3 Steps)

### Step 1: Open Terminal

Navigate to the project:

```bash
cd github-actions
```

### Step 2: Run Setup Script

**Windows:**
```bash
setup-and-test.bat
```

**Linux/Mac:**
```bash
chmod +x setup-and-test.sh
./setup-and-test.sh
```

This will:
- ✅ Compile the code
- ✅ Run linting (Checkstyle)
- ✅ Check formatting
- ✅ Execute 35+ tests
- ✅ Build the JAR file

### Step 3: Explore the Results

Check these locations:
- **JAR file**: `target/calculator-1.0-SNAPSHOT.jar`
- **Test reports**: `target/surefire-reports/`
- **Compiled classes**: `target/classes/`

## 📖 What You Have

### Complete Java Maven Project

✅ **Calculator Application**
- Addition, subtraction, multiplication, division
- Power calculation and square root
- State tracking (operation count, last result)

✅ **35+ JUnit 5 Tests**
- Nested test organization
- Parameterized tests
- Exception testing
- Integration tests

✅ **GitHub Actions CI/CD Pipeline**
- Automatic linting with Checkstyle
- Code formatting validation
- Automated testing
- JAR artifact building

✅ **Custom Code Quality Rules**
- Enforces naming conventions
- Member variables: `mstr`, `mi`, `mb`, `lstr`, `li`, `lb`
- Parameters: `p*`

## 🎯 What to Do Next

### Option 1: Run Tests Locally

```bash
# Navigate to project
cd github-actions

# Run all tests
mvn test

# View results
# Check: target/surefire-reports/
```

### Option 2: Push to GitHub

```bash
# Initialize git (if not already done)
cd github-actions
git init
git add .
git commit -m "Initial commit: Calculator with CI/CD"

# Create GitHub repository and push
git remote add origin https://github.com/YOUR_USERNAME/calculator-cicd.git
git branch -M main
git push -u origin main
```

Then:
1. Go to your GitHub repository
2. Click "Actions" tab
3. Watch the CI/CD pipeline run automatically!

### Option 3: Explore the Code

**Read the Calculator:**
```bash
# Open in your editor
code src/main/java/com/example/calculator/Calculator.java
```

**Read the Tests:**
```bash
# Open test file
code src/test/java/com/example/calculator/CalculatorTest.java
```

**Study the CI/CD Pipeline:**
```bash
# Open workflow file
code .github/workflows/ci-cd.yml
```

## 📚 Documentation Guide

Read in this order for best learning experience:

1. **QUICK_START.md** (5 min) → Get running quickly
2. **README.md** (15 min) → Understand everything
3. **PROJECT_SUMMARY.md** (10 min) → See complete overview
4. **ARCHITECTURE.md** (15 min) → Learn the design
5. **CONTRIBUTING.md** (10 min) → Learn how to contribute

## 🔧 Essential Maven Commands

```bash
# Compile
mvn compile

# Run tests
mvn test

# Check code style
mvn checkstyle:check

# Validate formatting
mvn formatter:validate

# Format code automatically
mvn formatter:format

# Build JAR
mvn package

# Clean and rebuild everything
mvn clean install
```

## 🎓 Learning Path

### Beginner Path

1. Run the setup script
2. Explore `Calculator.java`
3. Run tests: `mvn test`
4. Read test reports
5. Modify a method and see tests fail
6. Fix it and see tests pass

### Intermediate Path

1. Add a new operation (e.g., modulo)
2. Write tests for it
3. Ensure it follows naming conventions
4. Run checkstyle validation
5. Push to GitHub
6. Watch CI/CD pipeline

### Advanced Path

1. Add code coverage reporting (JaCoCo)
2. Add mutation testing (PIT)
3. Implement complex operations
4. Add performance benchmarks
5. Extend CI/CD pipeline

## 🐛 Common Issues

### ❌ "Maven not found"
**Solution:** Install Maven 3.6+ from https://maven.apache.org/

### ❌ "Java not found"
**Solution:** Install JDK 11+ from https://adoptium.net/

### ❌ Checkstyle errors
**Problem:** Variable doesn't follow naming convention

**Solution:** Use correct prefixes:
```java
// ❌ Bad
private int count;

// ✅ Good
private int miCount;
```

### ❌ Tests fail
**Solution:** 
1. Check `target/surefire-reports/`
2. Read error messages
3. Fix the code
4. Run `mvn test` again

## 🎯 Project Goals Achieved

✅ Maven project structure with proper organization  
✅ Calculator class with 6+ operations  
✅ JUnit 5 test suite with 35+ comprehensive tests  
✅ Custom Checkstyle configuration with naming rules  
✅ Code formatting setup and validation  
✅ Complete GitHub Actions CI/CD pipeline  
✅ Comprehensive documentation (1500+ lines)  
✅ Setup scripts for Windows and Linux/Mac  

## 📊 Project Statistics

- **Source Files**: 2 (Calculator.java, CalculatorTest.java)
- **Configuration Files**: 4 (pom.xml, checkstyle.xml, formatter-config.xml, .gitignore)
- **Documentation Files**: 6 (README, QUICK_START, PROJECT_SUMMARY, ARCHITECTURE, CONTRIBUTING, GET_STARTED)
- **Total Lines of Code**: ~1,500+
- **Test Cases**: 35+ (including parameterized variations)
- **Documentation Lines**: ~1,500+

## 🔄 Typical Workflow

```
1. Make changes to Calculator.java
         ↓
2. Write/update tests in CalculatorTest.java
         ↓
3. Run tests locally: mvn test
         ↓
4. Check style: mvn checkstyle:check
         ↓
5. Format code: mvn formatter:format
         ↓
6. Commit: git commit -m "Add feature"
         ↓
7. Push: git push
         ↓
8. Watch GitHub Actions run automatically
         ↓
9. Review results in GitHub Actions tab
```

## 🌟 Cool Features to Try

### 1. Break the Naming Convention
```java
// In Calculator.java, change:
private int miResultCount;
// to:
private int resultCount;

// Run checkstyle and watch it fail:
mvn checkstyle:check
```

### 2. Add a New Operation
```java
public double modulo(double pDividend, double pDivisor) {
    if (pDivisor == 0) {
        throw new ArithmeticException("Cannot modulo by zero");
    }
    double result = pDividend % pDivisor;
    updateResult(result);
    return result;
}
```

### 3. Write Parameterized Tests
```java
@ParameterizedTest
@CsvSource({"10, 3, 1", "15, 4, 3", "20, 7, 6"})
void testModulo(double pDividend, double pDivisor, double pExpected) {
    assertEquals(pExpected, calculator.modulo(pDividend, pDivisor), 0.0001);
}
```

## 💡 Pro Tips

1. **Use the setup scripts** - They validate everything in the correct order
2. **Read error messages carefully** - They usually tell you exactly what's wrong
3. **Check test reports** - They're in `target/surefire-reports/`
4. **Format before committing** - Run `mvn formatter:format`
5. **Test locally first** - Don't rely only on CI/CD

## 📞 Need Help?

- **Check README.md** for detailed information
- **Read QUICK_START.md** for step-by-step guide
- **Review ARCHITECTURE.md** to understand design
- **Check CONTRIBUTING.md** for coding standards

## 🎉 Success Criteria

You'll know everything is working when:

✅ `mvn test` shows all tests passing  
✅ `mvn checkstyle:check` shows no violations  
✅ `mvn formatter:validate` shows no formatting issues  
✅ `mvn package` creates a JAR file  
✅ GitHub Actions pipeline shows all green checkmarks  

## 🚀 You're Ready!

You now have a complete, production-ready Java Maven project with CI/CD!

**Next Command to Run:**
```bash
cd calculator-maven-project
setup-and-test.bat    # Windows
# OR
./setup-and-test.sh   # Linux/Mac
```

---

**Happy Learning! 🎓**

For detailed documentation, see [calculator-maven-project/README.md](calculator-maven-project/README.md)

