package com.poolc.springproject.poolcreborn.payload.request.book;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Builder(toBuilder = true)
@Getter
public class BookRequest {
    @NotEmpty
    private String title;
    private String isbn;
    @NotEmpty
    private String author;
    private String publisher;
}
