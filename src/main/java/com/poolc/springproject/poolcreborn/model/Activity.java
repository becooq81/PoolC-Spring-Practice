package com.poolc.springproject.poolcreborn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Activity {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
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

    public Activity(Long id, String title, LocalDate startDate, ActivityType activityType, int capacity, String schedule, int hours, List<String> tags) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.activityType = activityType;
        this.capacity = capacity;
        this.schedule = schedule;
        this.hours = hours;
        this.tags = tags;
    }

    public Activity() {}
}
