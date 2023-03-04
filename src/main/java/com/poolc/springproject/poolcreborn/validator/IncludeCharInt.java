package com.poolc.springproject.poolcreborn.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IncludeCharIntValidator.class)
public @interface IncludeCharInt {
    //error message
    public String message() default "사용자 이름은 숫자와 영어 모두 포함해야 합니다.";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
