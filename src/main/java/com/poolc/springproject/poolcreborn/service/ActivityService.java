package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.response.user.SimpleUserMajorDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.ActivityMapper;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
        activity.setUser(user);
        activityRepository.save(activity);
    }

    public void updateActivity(ActivityUpdateRequest activityUpdateRequest, Long currentActivityId) {
        Activity activity = activityRepository.findById(currentActivityId).get();
        activityMapper.updateActivityInfoFromRequest(activityUpdateRequest, activity);
        activityRepository.save(activity);
    }

    public Set<SimpleUserMajorDto> getParticipants(Activity activity) {
        return activity.getParticipants().stream()
                .map(p -> userMapper.buildSimpleUserMajorDtoFromUser(p))
                .collect(Collectors.toSet());
    }
}
