package com.mycompany.stringcalculator.service;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringCalculatorTest {

    private static final String NEGATIVE_NUMBERS_NOT_ALLOWED_MESSAGE =
            "Negative number are not allowed. Followed number cause exception: ";
    private final StringCalculator calculator = new StringCalculator();

    @Test
    public void shouldReturnZeroWhenStringIsEmpty() {
        //given
        int expectedSum = 0;
        String numbers = "";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldReturnNumberWhenStringIsSingleNumber() {
        //given
        int expectedSum = 9;
        String numbers = "9";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldSumTwoCommaDelimitedNumbers() {
        int expectedSum = 9;
        String numbers = "4,5";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldSumManyCommaDelimitedNumbers() {
        //given
        int expectedSum = 111;
        String numbers = "1,1,1,1,1,1,1,2,2,100";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldSumNumbersDelimitedWithCommaOrNewLineSign() {
        //given
        int expectedSum = 15;
        String numbers = "4,5\n2,4";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldSumNumberDelimitedWithCustomDelimiter() {
        //given
        int expectedSum = 11;
        String numbers = "//[;]\n5;6";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldSumManyNumberDelimitedWithCustomDelimiter() {
        //given
        int expectedSum = 242;
        String numbers = "//[***]\n1***1***29***100***111";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowNegativeNumbersNotAllowedExceptionWithMultipleNumbers() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(NEGATIVE_NUMBERS_NOT_ALLOWED_MESSAGE + "[-2]");
        String numbers = "//[;]\n1;-2";

        //when
        calculator.add(numbers);
    }

    @Test
    public void shouldThrowNegativeNumbersNotAllowedExceptionWithSingleNumber() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(NEGATIVE_NUMBERS_NOT_ALLOWED_MESSAGE + "[-1, -3]");
        String numbers = "//[;]\n-1;-3";

        //when
        calculator.add(numbers);
    }

    @Test
    public void shouldThrowNegativeNumbersNotAllowedExceptionWithDefaultDelimiters() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(NEGATIVE_NUMBERS_NOT_ALLOWED_MESSAGE + "[-8, -3, -1]");
        StringCalculator adder = new StringCalculator();
        String numbers = "2,3\n-8,-3,-1";

        //when
        calculator.add(numbers);
    }

    @Test
    public void shouldThrowNegativeNumbersNotAllowedExceptionForSingleNegativeNumber() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(NEGATIVE_NUMBERS_NOT_ALLOWED_MESSAGE + "[-1000]");
        String numbers = "-1000";

        //when
        calculator.add(numbers);
    }

    @Test
    public void shouldNotThrowNegativeNumbersNotAllowedExceptionForZero() {
        int expectedSum = 1;
        String numbers = "//[;]\n1;0";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldIgnoreNumbersGreaterThanThousandForMultiplyNumbers() {
        int expectedSum = 999;
        String numbers = "//[;]\n999;1001";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldNotIgnoreOneThousandNumberForMultiplyNumbers() {
        int expectedSum = 1999;
        String numbers = "//[;]\n999;1000";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldIgnoreOneThousandNumberForSingleNumber() {
        int expectedSum = 0;
        String numbers = "1001";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldNotIgnoreOneThousandForSingleNumber() {
        int expectedSum = 0;
        String numbers = "1000";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldSumNumbersWithMultipleCustomDelimiters() {
        int expectedSum = 242;
        String numbers = "//[;][aa]\n1;1;29aa100aa111";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }

    @Test
    public void shouldSumNumbersWithMultipleCustomDelimitersWithSpecialSigns() {
        int expectedSum = 242;
        String numbers = "//[()][^&*]\n1()1()29^&*100^&*111";

        //when
        int result = calculator.add(numbers);

        //then
        assertEquals(result, expectedSum);
    }
}
