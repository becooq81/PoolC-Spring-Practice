package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.RequestedParticipation;
import com.poolc.springproject.poolcreborn.payload.response.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.RequestedParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestedParticipationService {

    private final RequestedParticipationRepository requestedParticipationRepository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public boolean saveRequestedParticipation(String username, Activity activity) {
        if (userRepository.findByUsername(username).get().isClubMember()) {
            RequestedParticipation requestedParticipation = new RequestedParticipation();
            requestedParticipation.setUsername(username);
            requestedParticipation.setActivityTitle(activity.getTitle());
            requestedParticipationRepository.save(requestedParticipation);
            return true;
        }
        return false;
    }

    public List<RequestedParticipationDto> viewRequestedParticipation(Long activityId) {
        String activityTitle = activityRepository.findById(activityId).get().getTitle();
        List<RequestedParticipation> requests = requestedParticipationRepository.findByActivityTitle(activityTitle);
        return requests.stream()
                .map(r -> buildRequestedParticipationDtoFromRequestedParticipation(r))
                .collect(Collectors.toList());

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
