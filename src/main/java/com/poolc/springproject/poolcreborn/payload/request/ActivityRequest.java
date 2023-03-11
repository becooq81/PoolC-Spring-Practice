package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.model.ActivityType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder(toBuilder = true)
public class ActivityRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @NotEmpty
    private int capacity;

    @NotBlank
    private String schedule;

    @NotEmpty
    private int hours;

    private List<String> tags;

    @NotBlank
    private String plan;

    public ActivityRequest(String title, LocalDate startDate, ActivityType activityType, int capacity, String schedule, int hours, List<String> tags, String plan) {
        this.title = title;
        this.startDate = startDate;
        this.activityType = activityType;
        this.capacity = capacity;
        this.schedule = schedule;
        this.hours = hours;
        this.tags = tags;
        this.plan = plan;
    }

    public ActivityRequest() {
    }
}
