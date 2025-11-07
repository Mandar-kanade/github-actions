# Quick Start Guide

## 🚀 Get Up and Running in 5 Minutes

This guide will help you quickly set up and test the Calculator Maven project locally before pushing to GitHub.

## Prerequisites Check

Open a terminal and verify:

```bash
# Check Java (need JDK 11+)
java -version

# Check Maven (need 3.6+)
mvn -version

# Check Git
git --version
```

If any are missing, install them first!

## Quick Setup (3 Steps)

### Step 1: Navigate to Project Directory

```bash
cd github-actions
```

### Step 2: Run Setup Script

**On Windows:**
```bash
setup-and-test.bat
```

**On Linux/Mac:**
```bash
chmod +x setup-and-test.sh
./setup-and-test.sh
```

### Step 3: Review Results

The script will:
- ✅ Compile the code
- ✅ Run Checkstyle validation
- ✅ Check code formatting
- ✅ Run all tests
- ✅ Build the JAR file

## Manual Testing (Alternative)

If you prefer manual steps:

```bash
# 1. Compile
mvn clean compile

# 2. Run tests
mvn test

# 3. Check style
mvn checkstyle:check

# 4. Check formatting
mvn formatter:validate

# 5. Build
mvn package
```

## Understanding the CI/CD Pipeline

Once you push to GitHub, the workflow will:

```
┌─────────────────────────────────────────┐
│  1. LINT (Checkstyle)                   │
│     • Validates naming conventions      │
│     • Member vars: mstr, mi, mb...      │
│     • Parameters: p*                    │
└─────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────┐
│  2. FORMAT CHECK                        │
│     • Validates code formatting         │
│     • Ensures consistent style          │
└─────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────┐
│  3. TEST (JUnit 5)                      │
│     • Runs 35+ test cases               │
│     • Generates reports                 │
│     • Publishes results                 │
└─────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────┐
│  4. BUILD                               │
│     • Compiles project                  │
│     • Packages JAR                      │
│     • Uploads artifacts                 │
└─────────────────────────────────────────┘
```

## Setting Up GitHub Repository

### 1. Initialize Git Repository

```bash
cd github-actions
git init
git add .
git commit -m "Initial commit: Calculator Maven project with CI/CD"
```

### 2. Create GitHub Repository

Go to GitHub and create a new repository (e.g., `calculator-ci-cd`)

### 3. Push to GitHub

```bash
git remote add origin https://github.com/YOUR_USERNAME/calculator-ci-cd.git
git branch -M main
git push -u origin main
```

### 4. View GitHub Actions

After pushing:
1. Go to your repository on GitHub
2. Click on the "Actions" tab
3. Watch the CI/CD pipeline run automatically!

## Common Issues & Solutions

### ❌ Checkstyle Fails

**Problem:** `Member variable 'count' must start with mstr, mi, mb...`

**Solution:** Rename variables to follow conventions:
- `count` → `miCount`
- `result` → `lstrResult`
- `isValid` → `mbIsValid`

### ❌ Format Check Fails

**Problem:** Code formatting doesn't match standards

**Solution:** Run the formatter:
```bash
mvn formatter:format
git add .
git commit -m "Apply code formatting"
```

### ❌ Tests Fail

**Problem:** Tests are failing locally

**Solution:** 
1. Check test output: `target/surefire-reports/`
2. Review error messages
3. Fix implementation or tests
4. Re-run: `mvn test`

### ❌ Build Fails

**Problem:** Maven build errors

**Solution:**
```bash
# Clean and try again
mvn clean install

# Check Java version (needs 11+)
java -version

# Update dependencies
mvn clean install -U
```

## Project Structure At a Glance

```
calculator-maven-project/
├── 📁 .github/workflows/      # GitHub Actions CI/CD
├── 📁 src/main/java/          # Calculator source code
├── 📁 src/test/java/          # JUnit 5 tests
├── 📄 pom.xml                 # Maven configuration
├── 📄 checkstyle.xml          # Lint rules
├── 📄 formatter-config.xml    # Format rules
└── 📄 README.md               # Full documentation
```

## Testing Your Code Changes

When making changes:

```bash
# 1. Make your changes to Calculator.java

# 2. Update or add tests in CalculatorTest.java

# 3. Run quick validation
mvn clean test

# 4. Check style
mvn checkstyle:check

# 5. Commit and push
git add .
git commit -m "Add new feature"
git push
```

## Viewing CI/CD Results

After pushing to GitHub:

1. **Actions Tab**: See pipeline status
2. **Artifacts**: Download test reports and JARs
3. **Summary**: View test results summary
4. **Logs**: Debug any failures

## Next Steps

- ✅ Add more calculator operations
- ✅ Increase test coverage
- ✅ Add integration tests
- ✅ Experiment with the CI/CD pipeline
- ✅ Try breaking naming conventions (see what happens!)
- ✅ Add code coverage reporting
- ✅ Set up branch protection rules

## Getting Help

- Check `README.md` for detailed documentation
- Review GitHub Actions logs for errors
- Check `target/surefire-reports/` for test details

---

**Happy Learning! 🎓**

