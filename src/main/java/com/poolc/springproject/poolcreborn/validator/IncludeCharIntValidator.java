package com.poolc.springproject.poolcreborn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IncludeCharIntValidator implements ConstraintValidator<IncludeCharInt, String> {
    char[] chars;

    @Override
    public void initialize(IncludeCharInt constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        chars = value.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c) || Character.isDigit(c)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
