package com.poolc.springproject.poolcreborn.payload.request.participation;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(toBuilder = true)
public class ParticipationRequest {
    private String activity;
}
