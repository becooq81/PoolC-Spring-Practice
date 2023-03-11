package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.Activity;
import com.poolc.springproject.poolcreborn.payload.request.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.ActivityUpdateRequest;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.poolc.springproject.poolcreborn.security.SecurityUtil.getLoginUsername;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;

    @PostMapping("/new")
    public ResponseEntity<?> registerActivity(@RequestBody @Valid ActivityRequest activityRequest) {
        String username = getLoginUsername();
        activityService.saveActivity(activityRequest, username);
        return ResponseEntity.ok("Activity registered successfully!");
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> updateActivity(@PathVariable("id") Long currentActivityId, @RequestBody @Valid ActivityUpdateRequest activityUpdateRequest) {
        String username = getLoginUsername();
        Activity activity = activityRepository.findById(currentActivityId).get();
        if (activity.getUser().getUsername().equals(username)) {
            activityService.updateActivity(activityUpdateRequest, currentActivityId);
            return ResponseEntity.ok("Activity is updated successfully!");
        }
        return ResponseEntity.ok("Activity can be only updated by owner.");
    }
}
