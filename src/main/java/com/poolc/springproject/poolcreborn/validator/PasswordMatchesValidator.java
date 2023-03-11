package com.poolc.springproject.poolcreborn.validator;

import com.poolc.springproject.poolcreborn.payload.request.user.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserUpdateRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        if (obj.getClass().equals(SignupRequest.class)) {
            return validateSignupRequest(obj);
        }
        if (obj.getClass().equals(UserUpdateRequest.class)) {
            return validateUpdateUserRequest(obj);
        }
        return false;
    }

    public boolean validateSignupRequest(Object obj) {
        SignupRequest user = (SignupRequest) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
    public boolean validateUpdateUserRequest(Object obj) {
        UserUpdateRequest user = (UserUpdateRequest) obj;
        if (!user.passwordChanged()) {
            return true;
        }
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
