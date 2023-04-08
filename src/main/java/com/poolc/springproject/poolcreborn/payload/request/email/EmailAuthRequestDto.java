package com.poolc.springproject.poolcreborn.payload.request.email;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
public class EmailAuthRequestDto {
    @NotEmpty(message = "Enter your email.")
    public String email;
}
