package com.poolc.springproject.poolcreborn.validator;


import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReasonRequiredForNotApprovedValidator implements ConstraintValidator<ReasonRequiredForNotApproved, Object> {


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ParticipationRequest participationRequest = (ParticipationRequest) value;
        String reason = participationRequest.getReason();
        if (!participationRequest.getIsApproved()) {
            if (reason==null || reason.length() > 300 || reason.length() < 10) {
                return false;
            }
            return true;
        }
        return true;
    }
}
