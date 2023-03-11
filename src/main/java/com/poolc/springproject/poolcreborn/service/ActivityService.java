package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.Activity;
import com.poolc.springproject.poolcreborn.payload.request.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.ActivityUpdateRequest;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.util.ActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public void saveActivity(ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activityMapper.buildActivityFromRequest(activityRequest, activity);
        activityRepository.save(activity);
    }

    public void updateActivity(ActivityUpdateRequest activityUpdateRequest, Long currentActivityId) {
        Activity activity = activityRepository.findById(currentActivityId).get();
        activityMapper.updateActivityInfoFromRequest(activityUpdateRequest, activity);
        activityRepository.save(activity);
    }
}
