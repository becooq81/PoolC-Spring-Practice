package com.poolc.springproject.poolcreborn.model.user;

public enum ERole {
    ROLE_USER("준회원"),
    ROLE_ADMIN("임원진"),
    ROLE_CLUB_MEMBER("정회원");

    private final String value;

    ERole(String value) {this.value = value;}
    public String getValue() {return value;}
}
