package com.poolc.springproject.poolcreborn.model.activity;

import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.validator.NotExceedingCapacity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@NotExceedingCapacity
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

    @NotNull
    private int numParticipants;

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

    @OneToMany(mappedBy = "activity")
    private Set<Participation> participationList = new HashSet<>();

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

    public Set<User> getParticipants() {
        return this.participationList.stream()
                .map(Participation::getUser)
                .collect(Collectors.toSet());
    }
}
