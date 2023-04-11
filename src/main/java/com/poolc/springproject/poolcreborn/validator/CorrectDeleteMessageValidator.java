package com.poolc.springproject.poolcreborn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectDeleteMessageValidator implements ConstraintValidator<CorrectDeleteMessage, String> {
    @Override
    public void initialize(CorrectDeleteMessage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("I confirm the deletion.");
    }
}
