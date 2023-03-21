package com.poolc.springproject.poolcreborn.model.activity;

public enum ActivityType {
    SEMINAR("세미나"),
    STUDY("스터디");

    private final String value;
    ActivityType(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
