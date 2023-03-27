package com.poolc.springproject.poolcreborn.payload.request.participation;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder(toBuilder = true)
public class ParticipationRequest {
    @NotBlank
    private String activity;

    public ParticipationRequest(String activity) {
        this.activity = activity;
    }
    public ParticipationRequest() {}
}
