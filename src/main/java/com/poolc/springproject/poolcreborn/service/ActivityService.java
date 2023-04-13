package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.exception.InvalidRequestException;
import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.activity.ActivityType;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityUpdateRequest;
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
        Activity activity = activityRepository.findById(currentActivityId)
                .orElseThrow(() -> new InvalidRequestException(Message.ACTIVITY_DOES_NOT_EXIST));
        if (!username.equals(activity.getUser().getUsername())) {
            throw new InvalidRequestException(Message.UPDATE_ACTIVITY_ACCESS_DENIED);
        } else {
            activityMapper.updateActivityInfoFromRequest(activityUpdateRequest, activity);
            activityRepository.save(activity);
        }
    }

    public Set<UserMajorDto> getParticipants(Activity activity) {
        return activity.getParticipants().stream()
                .map(userMapper::buildUserMajorDtoFromUser)
                .collect(Collectors.toSet());
    }
}
