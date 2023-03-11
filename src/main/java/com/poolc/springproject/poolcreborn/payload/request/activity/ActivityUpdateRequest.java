package com.poolc.springproject.poolcreborn.payload.request.activity;

import com.poolc.springproject.poolcreborn.model.ActivityType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder(toBuilder = true)
public class ActivityUpdateRequest {

    private String title;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    private int capacity;

    private String schedule;

    private int hours;

    private List<String> tags = new ArrayList<>();

    private String plan;

    public ActivityUpdateRequest(String title, LocalDate startDate, ActivityType activityType, int capacity, String schedule, int hours, List<String> tags, String plan) {
        this.title = title;
        this.startDate = startDate;
        this.activityType = activityType;
        this.capacity = capacity;
        this.schedule = schedule;
        this.hours = hours;
        this.tags.addAll(tags);
        this.plan = plan;
    }
    public ActivityUpdateRequest() {}
}
