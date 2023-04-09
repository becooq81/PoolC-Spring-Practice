package com.poolc.springproject.poolcreborn.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ReasonRequiredForNotApprovedValidator.class)
@Documented
public @interface ReasonRequiredForNotApproved {

    String message() default "You must write your reason of 10~300 letters for participation in this type of seminar.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
