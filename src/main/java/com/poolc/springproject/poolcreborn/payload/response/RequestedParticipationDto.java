package com.poolc.springproject.poolcreborn.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class RequestedParticipationDto {

    private String username;
    private String activityTitle;
    private String major;

    public RequestedParticipationDto(String username, String activityTitle, String major) {
        this.username = username;
        this.activityTitle = activityTitle;
        this.major = major;
    }

    public RequestedParticipationDto() {}

}
