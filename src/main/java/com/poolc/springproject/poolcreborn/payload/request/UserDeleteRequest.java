package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.validator.CorrectDeleteMessage;
import lombok.Data;

@Data
public class UserDeleteRequest {

    @CorrectDeleteMessage
    private String message;
}
