package com.poolc.springproject.poolcreborn.model.participation;

public enum ParticipationType {
    PARTICIPATION("자유 신청형"),
    PARTICIPATION_REQUEST("요청 신청형");

    private final String value;

    ParticipationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
