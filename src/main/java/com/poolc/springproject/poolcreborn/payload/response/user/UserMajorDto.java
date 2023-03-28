package com.poolc.springproject.poolcreborn.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class UserMajorDto {
    private String username;
    private String name;
    private String major;
}
