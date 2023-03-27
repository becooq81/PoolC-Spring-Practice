package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.response.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.ParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.RequestedParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import com.poolc.springproject.poolcreborn.service.ParticipationService;
import com.poolc.springproject.poolcreborn.service.RequestedParticipationService;
import com.poolc.springproject.poolcreborn.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

import static com.poolc.springproject.poolcreborn.security.SecurityUtil.getLoginUsername;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;
    private final ParticipationRepository participationRepository;
    private final CustomMapper customMapper;
    private final UserRepository userRepository;
    private final ParticipationService participationService;
    private final RequestedParticipationRepository requestedParticipationRepository;
    private final RequestedParticipationService requestedParticipationService;

    @PostMapping("/new")
    public ResponseEntity<?> registerActivity(@RequestBody @Valid ActivityRequest activityRequest) {
        String username = getLoginUsername();
        activityService.saveActivity(activityRequest, username);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> viewActivity(@PathVariable("id") @Min(1) Long currentActivityId) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        return new ResponseEntity<>(customMapper.buildActivityDtoFromActivity(activity), HttpStatus.OK);
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> updateActivity(@PathVariable("id") @Min(1) Long currentActivityId, @RequestBody @Valid ActivityUpdateRequest activityUpdateRequest) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (activity.getUser().getUsername().equals(username)) {
            activityService.updateActivity(activityUpdateRequest, currentActivityId);
            return ResponseEntity.ok("성공적으로 수정했습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> signupForActivity(@PathVariable("id") @Min(1) Long currentActivityId,
                                                @RequestBody @Valid ParticipationRequest request) {
        String username = getLoginUsername();
        User user = userRepository.findByUsername(username).get();
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (!activity.getUser().getUsername().equals(username)) {
            // 세미나장 본인 아니면 신청 가능
            if (request.isApproved()) {
                participationService.saveParticipation(username, activity);
                return ResponseEntity.ok("성공적으로 신청되었습니다.");
            }
            else {
                requestedParticipationService.saveRequestedParticipation(username, activity);
                return ResponseEntity.ok("성공적으로 신청을 요청했습니다.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본인의 활동은 신청할 수 없습니다.");
    }
    @GetMapping("/{id}/participants/requested")
    public List<RequestedParticipationDto> viewParticipationRequests(@PathVariable("id") @Min(1) Long currentActivityId) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (activity.getUser().getUsername().equals(username)) {
            return requestedParticipationService.viewRequestedParticipation(currentActivityId);
        }
        return new ArrayList<>();
    }

    @PatchMapping("/{id}/participants/requested")
    public ResponseEntity<?> approveParticipants(@PathVariable("id") @Min(1) Long currentActivityId,
                                                 @RequestBody @Valid List<RequestedParticipationDto> requests) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (activity.getUser().getUsername().equals(username)) {
            // 세미나장 본인이면 신청 승인 가능
            participationService.approveParticipationRequestList(requests);
            return ResponseEntity.ok("성공적으로 요청을 승인했습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본인이 개설한 활동만 신청 요청자를 승인할 수 있습니다.");

    }
}
