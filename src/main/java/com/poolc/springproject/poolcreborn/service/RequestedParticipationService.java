package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.RequestedParticipation;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.response.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.RequestedParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestedParticipationService {

    private final RequestedParticipationRepository requestedParticipationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final CustomMapper customMapper;

    public void saveRequestedParticipation(String username, Activity activity) {
        RequestedParticipation requestedParticipation = new RequestedParticipation();
        requestedParticipation.setUsername(username);
        requestedParticipation.setActivityTitle(activity.getTitle());
        requestedParticipationRepository.save(requestedParticipation);
    }

    public List<RequestedParticipationDto> viewRequestedParticipation(Long activityId) {
        String activityTitle = activityRepository.findById(activityId).get().getTitle();
        List<RequestedParticipation> requests = requestedParticipationRepository.findByActivityTitle(activityTitle);
        return requests.stream()
                .map(r -> customMapper.buildRequestedParticipationDtoFromRequestedParticipation(r))
                .collect(Collectors.toList());

    }
}
