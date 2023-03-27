package com.poolc.springproject.poolcreborn.payload.request.participation;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder(toBuilder = true)
public class RestrictedParticipationRequest {

    @NotBlank
    private String activity;

    @NotBlank
    private String reason;


    public RestrictedParticipationRequest() {}

    public RestrictedParticipationRequest(String activity, String reason) {
        this.activity = activity;
        this.reason = reason;
    }


}
