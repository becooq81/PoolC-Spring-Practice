package com.poolc.springproject.poolcreborn.model.participation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
public class RequestedParticipation {

    @Id @GeneratedValue
    private Long id;

    @NotEmpty
    private String activityTitle;

    @NotEmpty
    private String username;

    public RequestedParticipation() {}
    public RequestedParticipation(String username, String activityTitle) {
        this.activityTitle = activityTitle;
        this.username = username;
    }

}
