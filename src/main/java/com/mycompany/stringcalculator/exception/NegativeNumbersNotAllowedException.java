package com.mycompany.stringcalculator.exception;

public class NegativeNumbersNotAllowedException extends RuntimeException {

    private static final String MESSAGE = "Negative number are not allowed. Followed number cause exception: ";

     public NegativeNumbersNotAllowedException(String numbers) {
        super(MESSAGE + numbers);
    }
}
