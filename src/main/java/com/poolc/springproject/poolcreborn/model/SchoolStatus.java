package com.poolc.springproject.poolcreborn.model;

public enum SchoolStatus {

    DEFAULT("일반 회원"),
    STOP("한 학기 비활동"),
    GRADUATED("졸업회원"),
    COMPLETED("수료회원");

    private final String value;

    SchoolStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
