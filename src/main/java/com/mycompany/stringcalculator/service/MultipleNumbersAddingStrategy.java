package com.mycompany.stringcalculator.service;

import com.mycompany.stringcalculator.exception.NegativeNumbersNotAllowedException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public class MultipleNumbersAddingStrategy implements StringAddingStrategy {

    private static final Pattern CUSTOM_DELIMITERS_WITH_NUMBERS_PATTERN = Pattern.compile("^//\\[(.+)\\]\n.*");
    private static final String CUSTOM_DELIMITER_SEQUENCE = "//\\[(.+)\\]\n";
    private static final String DEFAULT_SPLITTING_SEQUENCE = ",|\\n";

    @Override
    public int addNumbers(String numbers) {
        String splittingSequence = hasCustomDelimiters(numbers) ?
                getCustomSplittingSequence(numbers) :
                DEFAULT_SPLITTING_SEQUENCE;
        String numbersWithoutDelimiters = extractNumbersSequence(numbers);
        verifyIfContainsNegativeNumbers(numbersWithoutDelimiters, splittingSequence);
        return Stream.of(numbersWithoutDelimiters.split(splittingSequence)).mapToInt(Integer::parseInt)
                .filter(num -> num <= 1000)
                .sum();
    }

    private boolean hasCustomDelimiters(String numbers) {
        return CUSTOM_DELIMITERS_WITH_NUMBERS_PATTERN.matcher(numbers).matches();
    }

    private String getCustomSplittingSequence(String numbersWithDelimiters) {
        String[] delimiters = StringUtils.substringsBetween(numbersWithDelimiters, "[", "]");
        return Stream.of(delimiters).map(Pattern::quote)
                .collect(Collectors.joining("|"));
    }

    private String extractNumbersSequence(String numbers) {
        return numbers.replaceFirst(CUSTOM_DELIMITER_SEQUENCE, "");
    }

    private void verifyIfContainsNegativeNumbers(String numbers, String splittingSequence) {
        List<Integer> negativeNumbers = Stream.of(numbers.split(splittingSequence)).map(Integer::valueOf)
                                                                                   .filter(num -> num < 0)
                                                                                   .collect(Collectors.toList());
        if (!negativeNumbers.isEmpty()) {
            throw new NegativeNumbersNotAllowedException(negativeNumbers.toString());
        }
    }
}