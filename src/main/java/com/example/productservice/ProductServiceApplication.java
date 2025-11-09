package com.example.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Product Service Spring Boot application.
 */
@SpringBootApplication
public class ProductServiceApplication {

    // CHECKSTYLE:OFF - Spring Boot application main class
    /**
     * Main method to run the Spring Boot application.
     *
     * @param pArgs
     *            command line arguments
     */
    public static void main(String[] pArgs) {
        SpringApplication.run(ProductServiceApplication.class, pArgs);
    }
    // CHECKSTYLE:ON
}
