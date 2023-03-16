package com.poolc.springproject.poolcreborn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
public class Activity {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private LocalDate startDate;

    private String semester;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @NotNull
    @Max(100)
    private int capacity;

    @NotBlank
    @Enumerated
    private Day day;

    @NotNull
    @Max(10)
    private int hours;
    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private List<String> tags;

    @NotBlank
    private String plan;

    public Activity(String title, LocalDate startDate, ActivityType activityType, int capacity, Day day, int hours, List<String> tags) {
        this.title = title;
        this.startDate = startDate;
        this.activityType = activityType;
        this.capacity = capacity;
        this.day = day;
        this.hours = hours;
        this.tags = tags;
    }

    public Activity() {}
}
