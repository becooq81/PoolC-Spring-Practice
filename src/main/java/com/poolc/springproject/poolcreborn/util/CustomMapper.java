package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomMapper {
    @Autowired
    private ActivityService activityService;
    public CustomMapper() {}

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
