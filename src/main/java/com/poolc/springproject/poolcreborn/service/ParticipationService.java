package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.payload.response.user.SimpleUserDto;
import com.poolc.springproject.poolcreborn.repository.ParticipationRepository;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final UserMapper userMapper;

    public List<SimpleUserDto> getParticipants(Activity activity) {
        return activity.getParticipants().stream()
                .map(p -> userMapper.buildSimpleUserDtoFromUser(p))
                .collect(Collectors.toList());
    }
}
