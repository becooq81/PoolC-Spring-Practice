package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.service.ParticipationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomMapper {

    private final ParticipationService participationService;

    ActivityDto buildActivityDtoFromActivity(Activity activity) {
        if (activity == null) {
            return null;
        }
        ActivityDto activityDto = new ActivityDto();

        activityDto.setLead(activity.getUser().getUsername());
        activityDto.setStartDate(activity.getStartDate());
        activityDto.setHours(activity.getHours());
        activityDto.setCapacity(activity.getCapacity());
        activityDto.setTags(activity.getTags());
        activityDto.setPlan(activity.getPlan());
        activityDto.setParticipants(participationService.getParticipants(activity));

        return activityDto;
    }
}
