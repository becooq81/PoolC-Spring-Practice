package com.poolc.springproject.poolcreborn.payload.request.activity;

import com.poolc.springproject.poolcreborn.model.activity.ActivityType;
import com.poolc.springproject.poolcreborn.model.activity.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;
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
    @Max(100)
    private int capacity;
    private Day day = Day.UNDECIDED;

    @NotNull
    @Max(10)
    private int hours;

    private List<String> tags = new ArrayList<>();

    @NotBlank
    private String plan;

    public ActivityRequest(String title, LocalDate startDate, ActivityType activityType, int capacity, Day day, int hours, List<String> tags, String plan) {
        this.title = title;
        this.startDate = startDate;
        this.activityType = activityType;
        this.capacity = capacity;
        this.day = day;
        this.hours = hours;
        this.tags.addAll(tags);
        this.plan = plan;
    }

    public ActivityRequest() {
    }
}
