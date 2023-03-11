package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.Activity;
import com.poolc.springproject.poolcreborn.payload.request.ActivityRequest;
import com.poolc.springproject.poolcreborn.repository.ActivityRepository;
import com.poolc.springproject.poolcreborn.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;

    @PostMapping("/new")
    public ResponseEntity<?> registerActivity(@RequestBody @Valid ActivityRequest activityRequest) {
        activityService.saveActivity(activityRequest);
        return ResponseEntity.ok("Activity registered successfully!");
    }

}
