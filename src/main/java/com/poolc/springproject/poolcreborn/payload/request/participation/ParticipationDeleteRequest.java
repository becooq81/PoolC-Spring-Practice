package com.poolc.springproject.poolcreborn.payload.request.participation;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(toBuilder = true)
public class ParticipationDeleteRequest {
    private String username;
    private String activityTitle;

    public ParticipationDeleteRequest() {}
    public ParticipationDeleteRequest(String username, String activityTitle) {
        this.username = username;
        this.activityTitle = activityTitle;
    }
}
