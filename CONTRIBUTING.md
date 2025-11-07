# Contributing to Calculator Maven Project

Thank you for your interest in contributing! This document provides guidelines for contributing to the project.

## 🎯 Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/calculator-ci-cd.git`
3. Create a feature branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Test locally
6. Commit and push
7. Create a Pull Request

## 📋 Code of Conduct

- Be respectful and inclusive
- Focus on constructive feedback
- Help others learn and grow
- Follow project conventions

## 🔧 Development Guidelines

### Naming Conventions (MUST FOLLOW)

#### Member Variables
- **String members**: `mstrVariableName`
- **Integer members**: `miVariableName`
- **Boolean members**: `mbVariableName`
- **String locals**: `lstrVariableName`
- **Integer locals**: `liVariableName`
- **Boolean locals**: `lbVariableName`

Example:
```java
public class Example {
    private String mstrUsername;
    private int miCount;
    private boolean mbEnabled;
    
    public void process() {
        String lstrTempValue = "temp";
        int liTotal = 0;
    }
}
```

#### Parameter Names
All parameters MUST start with lowercase `p` followed by uppercase letter:

```java
public double calculate(double pFirstValue, double pSecondValue) {
    return pFirstValue + pSecondValue;
}
```

#### Method Names
- Use camelCase
- Start with lowercase letter
- Use descriptive names

```java
public void calculateTotal() { }
public boolean isValid() { }
public String getUserName() { }
```

### Code Style

#### Formatting Rules
- Use 4 spaces for indentation (no tabs)
- Maximum line length: 120 characters
- Opening braces on same line
- One statement per line

#### Example:
```java
public class Calculator {
    private int miCount;
    
    public void increment() {
        if (miCount < 100) {
            miCount++;
        }
    }
}
```

### Testing Requirements

#### Every New Feature Must Have Tests

For each new method, provide:
1. At least one positive test case
2. At least one negative/edge case test
3. Exception tests if applicable
4. Parameterized tests for multiple inputs

#### Test Organization

Use nested classes to organize tests:

```java
@Nested
@DisplayName("Feature Name Tests")
class FeatureTests {
    
    @Test
    @DisplayName("Should handle normal case")
    void testNormalCase() {
        // test implementation
    }
    
    @ParameterizedTest
    @CsvSource({"1, 2, 3", "4, 5, 9"})
    void testMultipleCases(int pA, int pB, int pExpected) {
        // test implementation
    }
}
```

## 🔄 Pull Request Process

### Before Submitting PR

1. **Run all tests locally:**
   ```bash
   mvn test
   ```

2. **Validate code style:**
   ```bash
   mvn checkstyle:check
   ```

3. **Check formatting:**
   ```bash
   mvn formatter:validate
   ```

4. **Build successfully:**
   ```bash
   mvn clean package
   ```

### PR Checklist

- [ ] All tests pass locally
- [ ] Checkstyle validation passes
- [ ] Code is properly formatted
- [ ] New tests added for new features
- [ ] Documentation updated if needed
- [ ] Commit messages are clear and descriptive
- [ ] No merge conflicts with main branch

### PR Template

When creating a PR, include:

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] All existing tests pass
- [ ] New tests added
- [ ] Manual testing completed

## Checklist
- [ ] Code follows naming conventions
- [ ] Checkstyle passes
- [ ] Formatting is correct
- [ ] Documentation updated
```

## 🐛 Reporting Bugs

### Bug Report Template

```markdown
**Describe the bug**
Clear description of the bug

**To Reproduce**
Steps to reproduce:
1. Go to '...'
2. Click on '...'
3. See error

**Expected behavior**
What should happen

**Actual behavior**
What actually happens

**Environment**
- Java version:
- Maven version:
- OS:

**Additional context**
Any other relevant information
```

## 💡 Suggesting Features

### Feature Request Template

```markdown
**Feature Description**
Clear description of the proposed feature

**Use Case**
Why is this feature needed?

**Proposed Solution**
How should it work?

**Alternatives Considered**
Other approaches you've thought about

**Additional Context**
Screenshots, examples, etc.
```

## 🧪 Testing Guidelines

### Test Naming Convention

```java
@Test
void testMethodName_StateUnderTest_ExpectedBehavior() {
    // Example: testDivide_WithZeroDenominator_ThrowsException
}
```

### Assertion Best Practices

```java
// Good - specific message
assertEquals(expected, actual, "Addition should return sum of inputs");

// Good - delta for doubles
assertEquals(3.14, result, 0.001, "Pi approximation");

// Good - clear exception testing
assertThrows(ArithmeticException.class, 
    () -> calculator.divide(10, 0),
    "Should throw ArithmeticException for division by zero");
```

### Test Data

- Use meaningful test values
- Test boundary conditions
- Test both positive and negative cases
- Test null/empty inputs where applicable

## 📝 Commit Message Guidelines

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

### Examples

```
feat(calculator): add modulo operation

- Implement modulo method
- Add comprehensive tests
- Update documentation

Closes #123
```

```
fix(division): handle division by zero correctly

Previously, division by zero crashed the application.
Now throws ArithmeticException with clear message.

Fixes #456
```

## 🔍 Code Review Process

### What Reviewers Look For

1. **Correctness**: Does the code work as intended?
2. **Tests**: Are there adequate tests?
3. **Style**: Does it follow conventions?
4. **Documentation**: Is it well-documented?
5. **Performance**: Are there obvious performance issues?
6. **Security**: Are there security concerns?

### Response Time

- Initial review: Within 2 business days
- Follow-up reviews: Within 1 business day

### Addressing Review Comments

1. Read all comments carefully
2. Ask questions if unclear
3. Make requested changes
4. Respond to each comment
5. Re-request review when ready

## 🎓 Learning Resources

### Java Best Practices
- [Effective Java by Joshua Bloch](https://www.oracle.com/java/technologies/effectivejava.html)
- [Java Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)

### JUnit 5
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JUnit 5 Best Practices](https://phauer.com/2019/modern-best-practices-testing-java/)

### Maven
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [Maven by Example](https://books.sonatype.com/mvnex-book/reference/index.html)

### GitHub Actions
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [GitHub Actions Best Practices](https://docs.github.com/en/actions/security-guides/security-hardening-for-github-actions)

## ❓ Questions?

- Check existing issues and discussions
- Review documentation
- Ask in pull request comments
- Create a new issue with `question` label

## 🏆 Recognition

Contributors will be recognized in:
- CONTRIBUTORS.md file
- Release notes
- Project README

Thank you for contributing! 🎉

