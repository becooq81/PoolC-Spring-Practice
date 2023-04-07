package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.exception.InvalidUserException;
import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.activity.ActivityType;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.payload.response.user.UserMajorDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.ActivityMapper;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import com.poolc.springproject.poolcreborn.util.Message;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void saveActivity(ActivityRequest activityRequest, String username) {
        Activity activity = new Activity();
        activityMapper.buildActivityFromRequest(activityRequest, activity);
        User user = userRepository.findByUsername(username).get();
        if (activity.getActivityType().equals(ActivityType.STUDY)) {
            user.addLeadingStudy(activity);
        }
        else {
            user.addLeadingSeminar(activity);
        }
        activity.setUser(user);
        activityRepository.save(activity);
    }

    public void updateActivity(String username, ActivityUpdateRequest activityUpdateRequest, Long currentActivityId) throws Exception {
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (!username.equals(activity.getUser().getUsername())) {
            throw new InvalidUserException(Message.UPDATE_ACTIVITY_ACCESS_DENIED);
        } else {
            activityMapper.updateActivityInfoFromRequest(activityUpdateRequest, activity);
            activityRepository.save(activity);
        }
    }

    public Set<UserMajorDto> getParticipants(Activity activity) {
        return activity.getParticipants().stream()
                .map(p -> userMapper.buildSimpleUserMajorDtoFromUser(p))
                .collect(Collectors.toSet());
    }
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
        activityDto.setParticipants(getParticipants(activity));

        return activityDto;
    }
}
