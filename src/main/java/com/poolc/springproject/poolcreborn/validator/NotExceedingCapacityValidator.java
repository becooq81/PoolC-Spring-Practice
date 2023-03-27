package com.poolc.springproject.poolcreborn.validator;

import com.poolc.springproject.poolcreborn.model.activity.Activity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotExceedingCapacityValidator implements ConstraintValidator<NotExceedingCapacity, Object> {


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Activity activity = (Activity) value;
        if (activity.getCapacity() < activity.getNumParticipants()) {
            return false;
        }
        return true;
    }
}
