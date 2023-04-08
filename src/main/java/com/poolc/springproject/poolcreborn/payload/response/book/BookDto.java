package com.poolc.springproject.poolcreborn.payload.response.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;


import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder
@Getter
public class BookDto {
    @NotEmpty
    private String title;
    private Integer isbn;
    private String author;
    private LocalDate published;
    private String publisher;
    private Integer category;
}
