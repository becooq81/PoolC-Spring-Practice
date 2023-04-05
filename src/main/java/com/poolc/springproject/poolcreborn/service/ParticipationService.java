package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.response.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.ParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.RequestedParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RequestedParticipationRepository requestedParticipationRepository;

    private final RequestedParticipationService requestedParticipationService;
    public boolean saveParticipation(User user, Activity activity) {
         if (!user.isClubMember()) {
             return false;
         }
         else {
             Participation participation = new Participation();
             participation.setUser(user);
             participation.setActivity(activity);
             activity.addParticipant(user);
             user.addParticipating(activity);
             participationRepository.save(participation);
             return true;
         }
    }
    public void approveParticipationRequest(RequestedParticipationDto requestedParticipationDto) {
        User user = userRepository.findByUsername(requestedParticipationDto.getUsername()).get();
        Activity activity = activityRepository.findByTitle(requestedParticipationDto.getActivityTitle()).get();
        Participation participation = new Participation(user, activity);
        participationRepository.save(participation);
    }
    public void approveParticipationRequestList(List<RequestedParticipationDto> requestedParticipationDtoList) {
        requestedParticipationDtoList.stream()
                .filter(r -> userRepository.findByUsername(r.getUsername()).get().isClubMember())
                .forEach(this::approveParticipationRequest);
    }

    public boolean signupRequestAvailable(String username, String activityTitle, ParticipationRequest request) {
        Activity activity = activityRepository.findByTitle(activityTitle).get();
        User user = userRepository.findByUsername(username).get();
        if (!participationRepository.existsByActivityAndUser(activity, user) && !requestedParticipationRepository.existsByActivityTitleAndUsername(username, activity.getTitle())) {
            if (request.getIsApproved()) {
                return saveParticipation(user, activity);
            }
            else {
                return requestedParticipationService.saveRequestedParticipation(username, activity);
            }
        }
        return false;
    }
}
