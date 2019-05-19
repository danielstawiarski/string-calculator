package com.mycompany.stringcalculator.service;


import java.util.regex.Pattern;

class StringCalculator {

    private static final Pattern INTEGER_NUMBER_PATTERN = Pattern.compile("^\\d+$");

    int add(String numbers) {
        return numbers.isEmpty() ? 0 : calculateSum(numbers);
    }

    private int calculateSum(String numbers) {
        StringAddingStrategy addingStrategy = isSingleNumber(numbers) ?
                new SingleNumberAddingStrategy() :
                new MultipleNumbersAddingStrategy();
        return addingStrategy.addNumbers(numbers);
    }

    private boolean isSingleNumber(String numbers) {
        return INTEGER_NUMBER_PATTERN.matcher(numbers).matches();
    }
}
