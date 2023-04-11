package com.poolc.springproject.poolcreborn.payload.request.book;

import com.poolc.springproject.poolcreborn.validator.CorrectDeleteMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
@Getter
@ToString
@Builder(toBuilder = true)
public class BookDeleteRequest {
    @CorrectDeleteMessage
    @NotEmpty
    private String message;

    public BookDeleteRequest() {}

    public BookDeleteRequest(String message) {
        this.message = message;
    }
}
