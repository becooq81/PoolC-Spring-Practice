package com.poolc.springproject.poolcreborn.payload.request.book;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder(toBuilder = true)
@Getter
public class BookRequest {
    @NotEmpty @Size(max = 50)
    private String title;
    private String isbn;
    @NotEmpty @Size(max = 30)
    private String author;
    private String publisher;

    public BookRequest() {}
    public BookRequest(String title, String isbn, String author, String publisher) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
    }
}
