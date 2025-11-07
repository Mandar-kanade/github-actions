package com.example.calculator;

/**
 * A simple calculator class that performs basic arithmetic operations. This
 * class follows custom naming conventions where member variables must start
 * with mstr, mi, mb, lstr, li, or lb prefixes.
 */
public class Calculator {

    private int miResultCount;
    private double lstrLastResult;

    /**
     * Constructor initializes the calculator.
     */
    public Calculator() {
            this.miResultCount = 0;
        this.lstrLastResult = 0.0;
    }

    /**
     * Adds two numbers.
     *
     * @param pFirstNumber
     *            the first number
     * @param pSecondNumber
     *            the second number
     * @return the sum of the two numbers
     */
    public double add(double pFirstNumber, double pSecondNumber) 
    {
        double result = pFirstNumber + pSecondNumber;
        updateResult(result);
        return result;
    }

    /**
     * Subtracts the second number from the first.
     *
     * @param pFirstNumber
     *            the first number
     * @param pSecondNumber
     *            the second number
     * @return the difference
     */
    public double subtract(double pFirstNumber, double pSecondNumber) {
        double result = pFirstNumber - pSecondNumber;
        updateResult(result);
        return result;
    }

    /**
     * Multiplies two numbers.
     *
     * @param pFirstNumber
     *            the first number
     * @param pSecondNumber
     *            the second number
     * @return the product
     */
    public double multiply(double pFirstNumber, double pSecondNumber) {
        double result = pFirstNumber * pSecondNumber;
        updateResult(result);
        return result;
    }

    /**
     * Divides the first number by the second.
     *
     * @param pNumerator
     *            the numerator
     * @param pDenominator
     *            the denominator
     * @return the quotient
     * @throws ArithmeticException
     *             if denominator is zero
     */
    public double divide(double pNumerator, double pDenominator) {
        if (pDenominator == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        double result = pNumerator / pDenominator;
        updateResult(result);
        return result;
    }

    /**
     * Calculates the power of a number.
     *
     * @param pBase
     *            the base number
     * @param pExponent
     *            the exponent
     * @return the result of base raised to exponent
     */
    public double power(double pBase, double pExponent) {
        double result = Math.pow(pBase, pExponent);
        updateResult(result);
        return result;
    }

    /**
     * Calculates the square root of a number.
     *
     * @param pNumber
     *            the number
     * @return the square root
     * @throws IllegalArgumentException
     *             if number is negative
     */
    public double squareRoot(double pNumber) {
        if (pNumber < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        double result = Math.sqrt(pNumber);
        updateResult(result);
        return result;
    }

    /**
     * Gets the count of operations performed.
     *
     * @return the result count
     */
    public int getResultCount() {
        return miResultCount;
    }

    /**
     * Gets the last calculated result.
     *
     * @return the last result
     */
    public double getLastResult() {
        return lstrLastResult;
    }

    /**
     * Resets the calculator state.
     */
    public void reset() {
        miResultCount = 0;
        lstrLastResult = 0.0;
    }

    /**
     * Updates the internal state after a calculation.
     *
     * @param pResult
     *            the calculated result
     */
    private void updateResult(double pResult) {
        lstrLastResult = pResult;
        miResultCount++;
    }
}
