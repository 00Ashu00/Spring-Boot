package com.learningspringboot.ashu.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator
        implements ConstraintValidator<PrimeNumber, Integer> {

    @Override
    public boolean isValid(
            Integer value,
            ConstraintValidatorContext context) {
        if (value == null || value <= 1) {
            return false;
        }
        for (int i = 2; i < value; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }
}