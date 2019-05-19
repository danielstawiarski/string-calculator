package com.mycompany.stringcalculator.service;

import com.mycompany.stringcalculator.exception.NegativeNumbersNotAllowedException;

public class SingleNumberAddingStrategy implements StringAddingStrategy {

    @Override
    public int addNumbers(String numbers) {
        int number = Integer.parseInt(numbers);
        if (number < 0) {
            throw new NegativeNumbersNotAllowedException(String.valueOf(number));
        } else if (number >= 1000) {
            return 0;
        }
        return number;
    }
}
