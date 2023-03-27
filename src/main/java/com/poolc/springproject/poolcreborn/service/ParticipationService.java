package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.response.user.SimpleUserDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.ParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public Set<SimpleUserDto> getParticipants(Activity activity) {
        return activity.getParticipants().stream()
                .map(p -> userMapper.buildSimpleUserDtoFromUser(p))
                .collect(Collectors.toSet());
    }

    public void saveParticipation(String username, ParticipationRequest request) {
        Participation participation = new Participation();
        participation.setUser(userRepository.findByUsername(username).get());

        Activity activity = activityRepository.findByTitle(request.getActivity()).get();
        participation.setActivity(activity);

        participationRepository.save(participation);
    }
}
