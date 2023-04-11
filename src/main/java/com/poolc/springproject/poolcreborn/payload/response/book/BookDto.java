package com.poolc.springproject.poolcreborn.payload.response.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder (toBuilder = true)
@Getter
@Setter
public class BookDto {
    @NotEmpty @Size(max = 50)
    private String title;
    private String image;
    private String isbn;
    @NotEmpty @Size(max = 30)
    private String author;
    private String publisher;

    public BookDto(String title, String image, String isbn, String author, String publisher) {
        this.title = title;
        this.image = image;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
    }
    public BookDto () {}
    public BookDto(JSONObject json) {
        this.title = json.getString("title");
        this.image = json.getString("image");
        this.isbn = json.getString("isbn");
        this.author = json.getString("author");
        this.publisher = json.getString("publisher");
    }

}
