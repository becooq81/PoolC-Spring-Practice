package com.poolc.springproject.poolcreborn.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SimpleUserDto {
    private String name;
    private boolean isAdmin;
}
