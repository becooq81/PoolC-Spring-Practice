package com.poolc.springproject.poolcreborn.payload.request.participation;

import com.poolc.springproject.poolcreborn.validator.ReasonRequiredForNotApproved;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ReasonRequiredForNotApproved
@RequiredArgsConstructor
public class ParticipationRequest {

    @NotNull
    private boolean isApproved;

    private String reason;
}
