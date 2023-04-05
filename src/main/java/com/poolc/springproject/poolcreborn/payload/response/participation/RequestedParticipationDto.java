package com.poolc.springproject.poolcreborn.payload.response.participation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class RequestedParticipationDto {

    private String username;
    private String activityTitle;
    private String major;
    private String reason;

    public RequestedParticipationDto(String username, String activityTitle, String major, String reason) {
        this.username = username;
        this.activityTitle = activityTitle;
        this.major = major;
        this.reason = reason;
    }

    public RequestedParticipationDto() {}

}
