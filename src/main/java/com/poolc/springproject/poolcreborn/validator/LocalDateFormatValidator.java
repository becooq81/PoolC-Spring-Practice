package com.poolc.springproject.poolcreborn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class LocalDateFormatValidator implements ConstraintValidator<LocalDateFormat, String> {
    @Override
    public void initialize(LocalDateFormat constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {return true;}
        try {
            LocalDate localDate = LocalDate.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
