package com.poolc.springproject.poolcreborn.payload.request;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Setter
@ToString
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
