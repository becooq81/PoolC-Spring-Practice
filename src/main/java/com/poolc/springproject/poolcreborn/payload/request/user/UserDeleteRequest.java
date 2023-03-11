package com.poolc.springproject.poolcreborn.payload.request.user;

import com.poolc.springproject.poolcreborn.validator.CorrectDeleteMessage;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder(toBuilder = true)
public class UserDeleteRequest {

    @CorrectDeleteMessage
    @NotEmpty
    private String message;

    public UserDeleteRequest() {}

    public UserDeleteRequest(String message) {
        this.message = message;
    }
}
