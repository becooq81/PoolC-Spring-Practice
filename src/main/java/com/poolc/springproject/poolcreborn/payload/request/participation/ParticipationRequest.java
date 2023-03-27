package com.poolc.springproject.poolcreborn.payload.request.participation;

import com.poolc.springproject.poolcreborn.validator.ReasonRequiredForNotApproved;
import lombok.*;

import javax.validation.constraints.NotNull;


@ReasonRequiredForNotApproved
@Getter
@ToString
@Builder(toBuilder = true)
public class ParticipationRequest {

    @NotNull
    private Boolean isApproved;

    private String reason;

    public ParticipationRequest() {}
    public ParticipationRequest(boolean isApproved, String reason) {
        this.isApproved = isApproved;
        this.reason = reason;
    }
}
