# Product Service - Spring Boot RESTful API

A Spring Boot 3.x RESTful web service for managing products with H2 in-memory database, demonstrating CI/CD practices with GitHub Actions, comprehensive testing, and custom code quality standards.

## 🎯 Project Overview

This project showcases:
- **Spring Boot 3.x**: Modern Spring Boot RESTful web service
- **Java 17**: Latest LTS Java version
- **JPA & Hibernate**: Database persistence with H2 in-memory database
- **REST API**: Full CRUD operations with JSON support
- **Comprehensive Testing**: Unit tests with JUnit 5 & Mockito, integration tests with MockMvc
- **GitHub Actions CI/CD**: Automated pipeline for linting, formatting, testing, and building
- **Custom Coding Standards**: Enforced via Checkstyle
- **Code Formatting**: Automated code formatting checks
- **CORS Enabled**: Configured for cross-origin requests

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)
- [Code Quality](#code-quality)
- [CI/CD Pipeline](#cicd-pipeline)
- [Coding Conventions](#coding-conventions)
- [Maven Commands](#maven-commands)
- [H2 Console Access](#h2-console-access)

## ✨ Features

### Product Management REST API
- **GET /api/products** - Retrieve all products
- **GET /api/products/{id}** - Retrieve product by ID
- **POST /api/products** - Create new product
- **PUT /api/products/{id}** - Update existing product
- **DELETE /api/products/{id}** - Delete product

### Product Entity Fields
- `id` (Long) - Auto-generated unique identifier
- `name` (String) - Product name (required)
- `category` (String) - Product category (required)
- `price` (Double) - Product price (required, >= 0)
- `stock` (Integer) - Stock quantity (required, >= 0)

### Testing
- **Unit Tests**: Service layer tests using JUnit 5 and Mockito
- **Integration Tests**: Controller tests using @SpringBootTest and MockMvc
- Validates CRUD operations and request/response behavior
- Tests against in-memory H2 database

### Technical Features
- Spring Boot 3.2.0 with Java 17
- H2 in-memory database with console access
- JPA/Hibernate with automatic schema generation
- Bean validation for entity fields
- CORS enabled for all origins
- JSON request/response format
- Comprehensive error handling

## 📁 Project Structure

```
github-actions/
├── .github/
│   └── workflows/
│       └── ci-cd.yml                 # GitHub Actions workflow
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/productservice/
│   │   │       ├── ProductServiceApplication.java    # Main application
│   │   │       ├── entity/
│   │   │       │   └── Product.java                  # Product entity
│   │   │       ├── repository/
│   │   │       │   └── ProductRepository.java        # JPA repository
│   │   │       ├── service/
│   │   │       │   └── ProductService.java           # Business logic
│   │   │       ├── controller/
│   │   │       │   └── ProductController.java        # REST endpoints
│   │   │       └── config/
│   │   │           └── CorsConfig.java               # CORS configuration
│   │   └── resources/
│   │       └── application.properties                # Application config
│   └── test/
│       └── java/
│           └── com/example/productservice/
│               ├── service/
│               │   └── ProductServiceTest.java       # Unit tests
│               └── controller/
│                   └── ProductControllerIntegrationTest.java  # Integration tests
├── checkstyle.xml                    # Checkstyle configuration
├── formatter-config.xml              # Code formatter configuration
├── pom.xml                           # Maven configuration
├── README.md                         # This file
└── CONTRIBUTING.md                   # Contributing guidelines
```

## 🔧 Prerequisites

- **Java**: JDK 17 or higher
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

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🌐 API Endpoints

Base URL: `http://localhost:8080/api/products`

### Get All Products
```bash
curl -X GET http://localhost:8080/api/products
```

### Get Product by ID
```bash
curl -X GET http://localhost:8080/api/products/1
```

### Create New Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","category":"Electronics","price":999.99,"stock":10}'
```

### Update Product
```bash
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Gaming Laptop","category":"Electronics","price":1499.99,"stock":5}'
```

### Delete Product
```bash
curl -X DELETE http://localhost:8080/api/products/1
```

## 🧪 Running Tests

### Run All Tests

```bash
mvn test
```

### Run Unit Tests Only

```bash
mvn test -Dtest=ProductServiceTest
```

### Run Integration Tests Only

```bash
mvn test -Dtest=ProductControllerIntegrationTest
```

### Generate Test Report

```bash
mvn surefire-report:report
```

The report will be available at: `target/site/surefire-report.html`

### Test Coverage

Generate JaCoCo code coverage report:

```bash
mvn clean test jacoco:report
```

Report location: `target/site/jacoco/index.html`

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

The GitHub Actions workflow (`ci-cd.yml`) includes:

### 1. **Lint Job**
- Runs Checkstyle validation
- Enforces custom naming conventions
- Uploads checkstyle reports as artifacts

### 2. **Format Check Job**
- Validates code formatting
- Ensures consistent code style

### 3. **Test Job**
- Executes all JUnit 5 tests (unit + integration)
- Generates test reports
- Uploads test results as artifacts
- Only runs if lint and format checks pass

### 4. **Build Job**
- Compiles the project
- Packages into JAR file
- Uploads build artifacts
- Only runs if all tests pass

### 5. **Code Quality Report Job**
- Aggregates results from all jobs
- Generates summary report

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
- `lstr` - Local string/double variables (e.g., `lstrLastResult`)
- `li` - Local integer variables (e.g., `liIndex`)
- `lb` - Local boolean variables (e.g., `lbValid`)

### Parameter Names
Must start with lowercase `p` followed by uppercase letter:
- `pFirstNumber`
- `pProduct`
- `pId`

**Example:**
```java
public Product addProduct(Product pProduct) {
    // implementation
}
```

### Method Names
- Lowercase first letter
- CamelCase for subsequent words
- Example: `getAllProducts`, `getProductById`, `addProduct`

### Class Names
- Uppercase first letter
- CamelCase
- Example: `Product`, `ProductService`, `ProductController`

## 🛠 Maven Commands

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Package without tests
mvn clean package -DskipTests

# Install to local repository
mvn clean install

# Run the application
mvn spring-boot:run
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

## 💾 H2 Console Access

The H2 database console is available at: `http://localhost:8080/h2-console`

**Connection Details:**
- **JDBC URL**: `jdbc:h2:mem:productdb`
- **Username**: `sa`
- **Password**: (leave empty)

## 📊 Response Formats

### Success Responses

**GET /api/products**
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "category": "Electronics",
    "price": 999.99,
    "stock": 10
  }
]
```

**POST /api/products** - Status: 201 Created
```json
{
  "id": 1,
  "name": "Laptop",
  "category": "Electronics",
  "price": 999.99,
  "stock": 10
}
```

**PUT /api/products/{id}** - Status: 200 OK
```json
{
  "id": 1,
  "name": "Gaming Laptop",
  "category": "Electronics",
  "price": 1499.99,
  "stock": 5
}
```

**DELETE /api/products/{id}** - Status: 204 No Content

### Error Responses

**404 Not Found**
```
Product not found
```

**400 Bad Request**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed"
}
```

## 🐛 Troubleshooting

### Application Won't Start

1. Ensure Java 17 is installed: `java -version`
2. Check if port 8080 is available
3. Verify Maven dependencies: `mvn dependency:resolve`

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
3. Verify H2 database configuration

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

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://site.mockito.org/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)

---

**Happy Coding! 🚀**
