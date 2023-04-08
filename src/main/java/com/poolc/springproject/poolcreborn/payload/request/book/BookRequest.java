package com.poolc.springproject.poolcreborn.payload.request.book;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder
@Getter
public class BookRequest {
    @NotEmpty
    private String title;
    private Integer isbn;
    @NotEmpty
    private String author;
    private LocalDate published;
    private String publisher;
    private Integer category;
}
