package com.poolc.springproject.poolcreborn.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RequestedParticipationDto {

    private String username;
    private String activityTitle;
    private String major;

}
