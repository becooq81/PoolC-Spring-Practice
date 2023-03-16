package com.poolc.springproject.poolcreborn.payload.request.activity;

import com.poolc.springproject.poolcreborn.model.ActivityType;
import com.poolc.springproject.poolcreborn.model.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder(toBuilder = true)
public class ActivityUpdateRequest {

    private ActivityType activityType;

    @Max(100)
    private int capacity;

    private Day day;

    @Max(12)
    private int hours;

    private List<String> tags = new ArrayList<>();

    private String plan;

    public ActivityUpdateRequest(ActivityType activityType, int capacity, Day day, int hours, List<String> tags, String plan) {
        this.activityType = activityType;
        this.capacity = capacity;
        this.day = day;
        this.hours = hours;
        this.tags.addAll(tags);
        this.plan = plan;
    }
    public ActivityUpdateRequest() {}
}
