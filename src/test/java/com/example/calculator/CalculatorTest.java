package com.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test class for Calculator. Demonstrates JUnit 5 features including nested
 * tests, parameterized tests, and custom display names.
 */
@DisplayName("Calculator Test Suite")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {

        @Test
        @DisplayName("Should add two positive numbers")
        void testAddPositiveNumbers() {
            double result = calculator.add(5.0, 3.0);
            assertEquals(8.0, result, 0.0001);
            assertEquals(8.0, calculator.getLastResult(), 0.0001);
            assertEquals(1, calculator.getResultCount());
        }

        @Test
        @DisplayName("Should add negative numbers")
        void testAddNegativeNumbers() {
            double result = calculator.add(-5.0, -3.0);
            assertEquals(-8.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should add zero")
        void testAddZero() {
            double result = calculator.add(5.0, 0.0);
            assertEquals(5.0, result, 0.0001);
        }

        @ParameterizedTest(name = "Adding {0} and {1} should equal {2}")
        @CsvSource({ "1.0, 2.0, 3.0", "10.5, 5.5, 16.0", "-3.0, 3.0, 0.0", "100.0, 200.0, 300.0" })
        void testAddParameterized(double pFirst, double pSecond, double pExpected) {
            assertEquals(pExpected, calculator.add(pFirst, pSecond), 0.0001);
        }
    }

    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {

        @Test
        @DisplayName("Should subtract two positive numbers")
        void testSubtractPositiveNumbers() {
            double result = calculator.subtract(10.0, 3.0);
            assertEquals(7.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should handle negative results")
        void testSubtractNegativeResult() {
            double result = calculator.subtract(3.0, 10.0);
            assertEquals(-7.0, result, 0.0001);
        }

        @ParameterizedTest(name = "{0} - {1} = {2}")
        @CsvSource({ "10.0, 5.0, 5.0", "100.0, 50.0, 50.0", "0.0, 5.0, -5.0", "5.5, 2.5, 3.0" })
        void testSubtractParameterized(double pFirst, double pSecond, double pExpected) {
            assertEquals(pExpected, calculator.subtract(pFirst, pSecond), 0.0001);
        }
    }

    @Nested
    @DisplayName("Multiplication Tests")
    class MultiplicationTests {

        @Test
        @DisplayName("Should multiply two positive numbers")
        void testMultiplyPositiveNumbers() {
            double result = calculator.multiply(5.0, 3.0);
            assertEquals(15.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should multiply by zero")
        void testMultiplyByZero() {
            double result = calculator.multiply(5.0, 0.0);
            assertEquals(0.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should multiply negative numbers")
        void testMultiplyNegativeNumbers() {
            double result = calculator.multiply(-5.0, -3.0);
            assertEquals(15.0, result, 0.0001);
        }

        @ParameterizedTest(name = "{0} * {1} = {2}")
        @CsvSource({ "2.0, 3.0, 6.0", "5.0, 5.0, 25.0", "-4.0, 2.0, -8.0", "10.0, 0.1, 1.0" })
        void testMultiplyParameterized(double pFirst, double pSecond, double pExpected) {
            assertEquals(pExpected, calculator.multiply(pFirst, pSecond), 0.0001);
        }
    }

    @Nested
    @DisplayName("Division Tests")
    class DivisionTests {

        @Test
        @DisplayName("Should divide two positive numbers")
        void testDividePositiveNumbers() {
            double result = calculator.divide(10.0, 2.0);
            assertEquals(5.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should throw exception when dividing by zero")
        void testDivideByZero() {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> calculator.divide(10.0, 0.0));
            assertEquals("Cannot divide by zero", exception.getMessage());
        }

        @Test
        @DisplayName("Should handle division resulting in decimal")
        void testDivideWithDecimalResult() {
            double result = calculator.divide(10.0, 3.0);
            assertEquals(3.3333, result, 0.0001);
        }

        @ParameterizedTest(name = "{0} / {1} = {2}")
        @CsvSource({ "10.0, 2.0, 5.0", "100.0, 4.0, 25.0", "7.0, 2.0, 3.5", "-10.0, 2.0, -5.0" })
        void testDivideParameterized(double pNumerator, double pDenominator, double pExpected) {
            assertEquals(pExpected, calculator.divide(pNumerator, pDenominator), 0.0001);
        }
    }

    @Nested
    @DisplayName("Power Tests")
    class PowerTests {

        @Test
        @DisplayName("Should calculate power of positive numbers")
        void testPowerPositive() {
            double result = calculator.power(2.0, 3.0);
            assertEquals(8.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should handle power of zero")
        void testPowerOfZero() {
            double result = calculator.power(5.0, 0.0);
            assertEquals(1.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should handle negative exponent")
        void testNegativeExponent() {
            double result = calculator.power(2.0, -2.0);
            assertEquals(0.25, result, 0.0001);
        }

        @ParameterizedTest(name = "{0} ^ {1} = {2}")
        @CsvSource({ "2.0, 2.0, 4.0", "3.0, 3.0, 27.0", "10.0, 2.0, 100.0", "5.0, 0.0, 1.0" })
        void testPowerParameterized(double pBase, double pExponent, double pExpected) {
            assertEquals(pExpected, calculator.power(pBase, pExponent), 0.0001);
        }
    }

    @Nested
    @DisplayName("Square Root Tests")
    class SquareRootTests {

        @Test
        @DisplayName("Should calculate square root of positive number")
        void testSquareRootPositive() {
            double result = calculator.squareRoot(16.0);
            assertEquals(4.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should handle square root of zero")
        void testSquareRootZero() {
            double result = calculator.squareRoot(0.0);
            assertEquals(0.0, result, 0.0001);
        }

        @Test
        @DisplayName("Should throw exception for negative number")
        void testSquareRootNegative() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> calculator.squareRoot(-4.0));
            assertEquals("Cannot calculate square root of negative number", exception.getMessage());
        }

        @ParameterizedTest(name = "√{0} = {1}")
        @CsvSource({ "4.0, 2.0", "9.0, 3.0", "25.0, 5.0", "100.0, 10.0", "1.0, 1.0" })
        void testSquareRootParameterized(double pNumber, double pExpected) {
            assertEquals(pExpected, calculator.squareRoot(pNumber), 0.0001);
        }
    }

    @Nested
    @DisplayName("State Management Tests")
    class StateTests {

        @Test
        @DisplayName("Should track result count")
        void testResultCount() {
            assertEquals(0, calculator.getResultCount());
            calculator.add(1.0, 2.0);
            assertEquals(1, calculator.getResultCount());
            calculator.multiply(3.0, 4.0);
            assertEquals(2, calculator.getResultCount());
        }

        @Test
        @DisplayName("Should store last result")
        void testLastResult() {
            calculator.add(5.0, 3.0);
            assertEquals(8.0, calculator.getLastResult(), 0.0001);
            calculator.multiply(2.0, 3.0);
            assertEquals(6.0, calculator.getLastResult(), 0.0001);
        }

        @Test
        @DisplayName("Should reset calculator state")
        void testReset() {
            calculator.add(5.0, 3.0);
            calculator.multiply(2.0, 3.0);
            assertEquals(2, calculator.getResultCount());

            calculator.reset();

            assertEquals(0, calculator.getResultCount());
            assertEquals(0.0, calculator.getLastResult(), 0.0001);
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should perform complex calculation sequence")
        void testComplexCalculation() {
            // (10 + 5) * 2 = 30
            double step1 = calculator.add(10.0, 5.0);
            double step2 = calculator.multiply(step1, 2.0);
            assertEquals(30.0, step2, 0.0001);
            assertEquals(2, calculator.getResultCount());
        }

        @Test
        @DisplayName("Should handle mixed operations")
        void testMixedOperations() {
            calculator.add(100.0, 50.0);
            calculator.subtract(200.0, 100.0);
            calculator.multiply(5.0, 5.0);
            calculator.divide(100.0, 4.0);
            calculator.power(2.0, 3.0);
            calculator.squareRoot(16.0);

            assertEquals(6, calculator.getResultCount());
            assertEquals(4.0, calculator.getLastResult(), 0.0001);
        }
    }
}
