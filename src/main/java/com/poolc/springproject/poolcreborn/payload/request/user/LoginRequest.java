package com.poolc.springproject.poolcreborn.payload.request.user;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder(toBuilder = true)
public class LoginRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {}
}
