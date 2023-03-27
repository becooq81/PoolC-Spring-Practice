package com.poolc.springproject.poolcreborn.model.participation;

import java.io.Serializable;

public class ParticipationId implements Serializable {
    private Long activity;
    private Long user;

    public ParticipationId() {}
    public ParticipationId(Long activity, Long user) {
        this.activity = activity;
        this.user = user;
    }

}
