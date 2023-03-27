package com.poolc.springproject.poolcreborn.model.activity;

public enum Day {

    MONDAY("매주 월요일"),
    TUESDAY("매주 화요일"),
    WEDNESDAY("매주 수요일"),
    THURSDAY("매주 목요일"),
    FRIDAY("매주 금요일"),
    SATURDAY("매주 토요일"),
    SUNDAY("매주 일요일"),
    UNDECIDED("미정");

    private final String value;

    Day(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
