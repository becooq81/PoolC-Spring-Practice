package com.poolc.springproject.poolcreborn.payload.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDto {
    private String name;
    private String major;
    private String description;
}