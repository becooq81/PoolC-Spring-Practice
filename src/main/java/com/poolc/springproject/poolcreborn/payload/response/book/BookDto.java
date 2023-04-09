package com.poolc.springproject.poolcreborn.payload.response.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder (toBuilder = true)
@Getter
@Setter
public class BookDto {
    @NotEmpty
    private String title;
    private Integer isbn;
    private String author;
    private LocalDate published;
    private String publisher;
/*
    public BookDto buildBookDtoFromJSON(JSONObject item) {
        BookDto bookDto = BookDto.builder()
                .title((String) item.get("d_titl"))
                .isbn((Integer) item.get("isbn"))
                .author((String) item.get("d_auth"))
                .isbn((Integer) item.get("d_isbn"))
                .published((LocalDate) item.get("d_dafr"))
                .publisher((String) item.get("d_publ"))
                .category((Integer) item.get("d_catg"))
                .build();
        return bookDto;
    }*/
    public BookDto(JSONObject json) {
        this.title = json.getString("title");
        this.isbn = json.getInt("isbn");
        this.author = json.getString("author");
        this.published = LocalDate.parse(json.getString("pubdate"));
        this.publisher = json.getString("publisher");
    }

}
