package com.poolc.springproject.poolcreborn.model.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.validator.NotExceedingCapacity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    @JsonIgnore
    private User user;

    @NotNull
    private LocalDate startDate;

    @Max(2) @Min(1)
    private int semester;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @NotNull
    @Max(100)
    private int capacity;

    @Enumerated
    private Day day = Day.UNDECIDED;

    @NotNull
    @Max(10)
    private int hours;
    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private List<String> tags;

    @NotBlank
    private String plan;

    @OneToMany(mappedBy = "activity")
    private Set<Participation> participationList = new HashSet<>();

    private int sessions;
    private int numParticipants;
    private boolean isAvailable;
    public Activity(String title, LocalDate startDate, ActivityType activityType, int capacity, Day day, int hours, List<String> tags, String plan) {
        this.title = title;
        this.startDate = startDate;
        this.activityType = activityType;
        this.capacity = capacity;
        this.day = day;
        this.hours = hours;
        this.tags = tags;
        this.plan = plan;
        this.sessions = 0;
        this.numParticipants = 0;
        if (startDate.getMonthValue() >= 9) {
            this.semester = 2;
        }
        else {
            this.semester = 1;
        }
        if (this.numParticipants < capacity) {
            this.isAvailable = true;
        }
        else {
            this.isAvailable = false;
        }
    }

    public Activity() {
        sessions=0;
    }
    public void addParticipant(User user) {
        this.participationList.add(new Participation(user, this));
        this.numParticipants += 1;
        if (this.numParticipants >= this.capacity) {
            isAvailable = false;
        }
    }
    public Set<User> getParticipants() {
        if (this.participationList==null) {
            return new HashSet<>();
        }
        return this.participationList.stream()
                .map(Participation::getUser)
                .collect(Collectors.toSet());
    }
    public void addSession() {
        this.sessions += 1;
    }
    public int getTotalHours() {
        return this.sessions*this.hours;
    }

}
