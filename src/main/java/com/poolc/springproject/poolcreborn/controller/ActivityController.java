package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.response.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.payload.response.user.UserDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.repository.ParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.RequestedParticipationRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import com.poolc.springproject.poolcreborn.service.ParticipationService;
import com.poolc.springproject.poolcreborn.service.RequestedParticipationService;
import com.poolc.springproject.poolcreborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

import static com.poolc.springproject.poolcreborn.security.SecurityUtil.getLoginUsername;
import static com.poolc.springproject.poolcreborn.util.Message.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;
    private final ParticipationService participationService;
    private final RequestedParticipationService requestedParticipationService;
    private final ParticipationRepository participationRepository;
    private final UserService userService;
    private final RequestedParticipationRepository requestedParticipationRepository;

    @PostMapping("/new")
    public ResponseEntity<?> registerActivity(@RequestBody @Valid ActivityRequest activityRequest) {
        String username = getLoginUsername();
        activityService.saveActivity(activityRequest, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFUL_CREATED_ACTIVITY);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> viewActivity(@PathVariable("id") @Min(1) Long currentActivityId) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        return new ResponseEntity<>(activityService.buildActivityDtoFromActivity(activity), HttpStatus.OK);
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> updateActivity(@PathVariable("id") @Min(1) Long currentActivityId, @RequestBody @Valid ActivityUpdateRequest activityUpdateRequest) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (activity.getUser().getUsername().equals(username)) {
            activityService.updateActivity(activityUpdateRequest, currentActivityId);
            return ResponseEntity.ok(SUCCESSFUL_UPDATE);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UPDATE_ACTIVITY_ACCESS_DENIED);
    }

    @PostMapping("/{id}/participants")
    public ResponseEntity<?> signupForActivity(@PathVariable("id") @Min(1) Long currentActivityId,
                                                @RequestBody @Valid ParticipationRequest request) {
        Activity activity = activityRepository.findById(currentActivityId).get();
        String username = getLoginUsername();
        HttpStatus httpStatus;
        String message;

        if (!activity.isAvailable() || activity.getUser().getUsername().equals(username)) {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = FAIL_SIGNUP_ACTIVITY;
        }
        if (participationService.signupRequestAvailable(username, activity.getTitle(), request)) {
            httpStatus = HttpStatus.ACCEPTED;
            message = SUCCESSFUL_SIGNUP_ACTIVITY;
        }
        else {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = FAIL_SIGNUP_ACTIVITY;
        }
        return ResponseEntity.status(httpStatus).body(message);
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

    @PostMapping("/{id}/participants/requested")
    public ResponseEntity<?> approveParticipants(@PathVariable("id") @Min(1) Long currentActivityId,
                                                 @RequestBody @Valid List<RequestedParticipationDto> requests) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (activity.getUser().getUsername().equals(username)) {
            // 세미나장 본인이면 신청 승인 가능
            participationService.approveParticipationRequestList(requests);
            return ResponseEntity.ok(SUCCESSFUL_REQUEST_APPROVAL);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APPROVAL_ACCESS_DENIED);

    }
}
