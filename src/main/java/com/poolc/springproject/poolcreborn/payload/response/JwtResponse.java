package com.poolc.springproject.poolcreborn.payload.response;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@ToString
public class JwtResponse {

    private final static String BEARER = "Bearer";
    private String token;
    private String type = BEARER;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

