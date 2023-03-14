package com.poolc.springproject.poolcreborn.payload.request.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poolc.springproject.poolcreborn.model.ActivityType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder(toBuilder = true)
public class ActivityRequest {

    @NotEmpty
    private String title;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private ActivityType activityType;

    @NotNull
    private int capacity;

    @NotBlank
    private String schedule;

    @NotNull
    private int hours;

    private List<String> tags = new ArrayList<>();

    @NotBlank
    private String plan;

    public ActivityRequest(String title, LocalDate startDate, ActivityType activityType, int capacity, String schedule, int hours, List<String> tags, String plan) {
        this.title = title;
        this.startDate = startDate;
        this.activityType = activityType;
        this.capacity = capacity;
        this.schedule = schedule;
        this.hours = hours;
        this.tags.addAll(tags);
        this.plan = plan;
    }

    public ActivityRequest() {
    }
}
