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
    private boolean isApproved;

    private String reason;
}
