package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.exception.InvalidUserException;
import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.response.participation.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.ParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.poolc.springproject.poolcreborn.util.Message;


@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    public void saveParticipation(User user, Activity activity) {
         if (user.isClubMember()) {
             Participation participation = new Participation();
             participation.setUser(user);
             participation.setActivity(activity);
             activity.addParticipant(user);
             user.addParticipating(activity);
             participationRepository.save(participation);
         }
    }
    public void removeParticipation(String username, Long activityId) {
        User user = userRepository.findByUsername(username).orElse(null);
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (user != null && activity != null) {
            Participation participation = participationRepository.findByUserAndActivity(user, activity).orElse(null);
            participationRepository.deleteById(participation.getId());
        }
    }
    public Participation findParticipation(String username, Long activityId) {
        User user = userRepository.findByUsername(username).orElse(null);
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (user == null || activity == null) { return null; }
        else {
            return participationRepository.findByUserAndActivity(user, activity).orElse(null);
        }
    }
    private void approveParticipationRequest(RequestedParticipationDto requestedParticipationDto) {
        User user = userRepository.findByUsername(requestedParticipationDto.getUsername()).orElse(null);
        Activity activity = activityRepository.findByTitle(requestedParticipationDto.getActivityTitle()).orElse(null);
        if (user != null && activity != null && activity.isAvailable()) {
            Participation participation = participationRepository.findByUserAndActivity(user, activity).get();
            participation.setApproved(true);
        }
    }
    public void approveParticipationRequestList(List<RequestedParticipationDto> requestedParticipationDtoList) {
        requestedParticipationDtoList.stream()
                .filter(r -> userRepository.findByUsername(r.getUsername()).get().isClubMember())
                .forEach(this::approveParticipationRequest);
    }

    public void signupParticipation(String username, String activityTitle, ParticipationRequest request) throws Exception{
        Activity activity = activityRepository.findByTitle(activityTitle).orElse(null);
        User user = userRepository.findByUsername(username).orElse(null);
        if (participationRepository.existsByUserAndActivity(user, activity)) {
            throw new InvalidUserException(Message.FAIL_SIGNUP_ACTIVITY);
        }
        else if (activity != null && user != null) {
            saveParticipation(user, activity);
        }
    }
    public RequestedParticipationDto buildRequestedParticipationDtoFromRequestedParticipation(Participation participation) {
        if (participation == null || !participation.isApproved()) {
            return null;
        }
        RequestedParticipationDto requestedParticipationDto = new RequestedParticipationDto();

        requestedParticipationDto.setActivityTitle(participation.getActivity().getTitle());
        requestedParticipationDto.setUsername(participation.getActivity().getUser().getUsername());
        requestedParticipationDto.setMajor(userRepository.findByUsername(participation.getUser().getUsername()).get().getMajor());
        requestedParticipationDto.setReason(participation.getReason());

        return requestedParticipationDto;
    }
    public List<RequestedParticipationDto> viewRequestedParticipation(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity != null) {
            List<Participation> requests = participationRepository.findByActivityTitleAndIsApproved(activity.getTitle(), false);
            return requests.stream()
                    .map(r -> buildRequestedParticipationDtoFromRequestedParticipation(r))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
