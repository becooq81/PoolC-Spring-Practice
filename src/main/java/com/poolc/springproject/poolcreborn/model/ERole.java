package com.poolc.springproject.poolcreborn.model;

public enum ERole {
    ROLE_USER("정회원"),
    ROLE_ADMIN("임원진"),
    ROLE_TEMPORARY_USER("준회원");

    private final String value;

    ERole(String value) {this.value = value;}
    public String getValue() {return value;}
}
