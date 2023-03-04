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
        int strCount = 0;
        int intCount = 0;
        for (char c : chars) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return false;
            }
            if (Character.isLetter(c)) strCount++;
            if (Character.isDigit(c)) intCount++;
        }
        if (strCount==0 || intCount==0) {
            return false;
        }
        return true;
    }
}
