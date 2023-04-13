package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationDeleteRequest;
import com.poolc.springproject.poolcreborn.payload.response.participation.RequestedParticipationDto;
import com.poolc.springproject.poolcreborn.payload.response.activity.ActivityDto;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import com.poolc.springproject.poolcreborn.service.ParticipationService;
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
import com.poolc.springproject.poolcreborn.util.Message;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;
    private final ParticipationService participationService;
    private final CustomMapper customMapper;

    @PostMapping("/new")
    public ResponseEntity<?> registerActivity(@RequestBody @Valid ActivityRequest activityRequest) {
        String username = getLoginUsername();
        activityService.saveActivity(activityRequest, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Message.SUCCESSFUL_CREATED_ACTIVITY);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> viewActivity(@PathVariable("id") @Min(1) Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        return new ResponseEntity<>(customMapper.buildActivityDtoFromActivity(activity), HttpStatus.OK);
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> updateActivity(@PathVariable("id") @Min(1) Long activityId,
                                            @RequestBody @Valid ActivityUpdateRequest activityUpdateRequest) {
        String username = getLoginUsername();

        try {
            activityService.updateActivity(username, activityUpdateRequest, activityId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Message.SUCCESSFUL_UPDATE_ACTIVITY);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/{id}/participants")
    public ResponseEntity<?> signupForActivity(@PathVariable("id") @Min(1) Long activityId,
                                                @RequestBody @Valid ParticipationRequest request) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        String username = getLoginUsername();

        try {
            participationService.signupParticipation(username, activity.getTitle(), request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Message.SUCCESSFUL_SIGNUP_ACTIVITY);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/{id}/participants/requested")
    public List<RequestedParticipationDto> viewParticipationRequests(@PathVariable("id") @Min(1) Long activityId) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(activityId).get();
        if (activity.getUser().getUsername().equals(username)) {
            return participationService.viewRequestedParticipation(activityId);
        }
        return new ArrayList<>();
    }

    @PostMapping("/{id}/participants/requested")
    public ResponseEntity<?> approveParticipants(@PathVariable("id") @Min(1) Long activityId,
                                                 @RequestBody @Valid List<RequestedParticipationDto> requests) throws Exception {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(activityId).get();

        HttpStatus httpStatus;
        String message;

        if (activity.getUser().getUsername().equals(username)) {
            participationService.approveParticipationRequestList(requests);
            httpStatus = HttpStatus.OK;
            message = Message.SUCCESSFUL_REQUEST_APPROVAL;
        }
        else {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = Message.APPROVAL_ACCESS_DENIED;
        }
        return ResponseEntity.status(httpStatus)
                .body(message);
    }

    @PatchMapping("/{id}/participants")
    public ResponseEntity<?> removeParticipants(@PathVariable("id") @Min(1) Long activityId,
                                                @RequestBody @Valid ParticipationDeleteRequest deleteRequest) {
        String username = getLoginUsername();

        try {
            if (participationService.findParticipation(username, activityId) != null) {
                participationService.removeParticipation(username, activityId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Message.SUCCESSFUL_DELETE_PARTICIPATION);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.PARTICIPATION_DELETE_DENIED);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
