package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.RequestedParticipation;
import com.poolc.springproject.poolcreborn.payload.response.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import com.poolc.springproject.poolcreborn.service.ParticipationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomMapper {

    private final ActivityService activityService;
    private final UserRepository userRepository;

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

    public RequestedParticipationDto buildRequestedParticipationDtoFromRequestedParticipation(RequestedParticipation requestedParticipation) {
        if (requestedParticipation == null) {
            return null;
        }
        RequestedParticipationDto requestedParticipationDto = new RequestedParticipationDto();

        requestedParticipationDto.setActivityTitle(requestedParticipation.getActivityTitle());
        requestedParticipationDto.setUsername(requestedParticipation.getUsername());
        requestedParticipationDto.setMajor(userRepository.findByUsername(requestedParticipation.getUsername()).get().getMajor());

        return requestedParticipationDto;
    }
}
