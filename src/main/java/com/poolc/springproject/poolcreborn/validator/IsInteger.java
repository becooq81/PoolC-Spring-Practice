package com.poolc.springproject.poolcreborn.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsIntegerValidator.class)
public @interface IsInteger {

    String message() default "The student id must be composed of integers.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
