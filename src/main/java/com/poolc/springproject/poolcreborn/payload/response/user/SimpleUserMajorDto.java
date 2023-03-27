package com.poolc.springproject.poolcreborn.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class SimpleUserMajorDto {
    private String name;
    private String major;
}
