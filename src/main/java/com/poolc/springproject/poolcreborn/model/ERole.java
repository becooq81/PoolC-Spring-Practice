package com.poolc.springproject.poolcreborn.model;

public enum ERole {
    USER("User"),
    ADMIN("Admin");

    private final String value;

    ERole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}