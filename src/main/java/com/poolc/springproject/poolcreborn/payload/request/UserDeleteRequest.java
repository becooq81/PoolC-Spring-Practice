package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.validator.CorrectDeleteMessage;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class UserDeleteRequest {

    @CorrectDeleteMessage
    private String message;

    public UserDeleteRequest() {}

    public UserDeleteRequest(String message) {
        this.message = message;
    }
}
