package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CustomMapper {
    private final ActivityService activityService;

    public ActivityDto buildActivityDtoFromActivity(Activity activity) {
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
        activityDto.setParticipants(activityService.getParticipants(activity));

        return activityDto;
    }
}
