package com.poolc.springproject.poolcreborn.payload.request;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
public class BookSearchRequest {
    @Length(min = 1, max = 30)
    private String query;

    public BookSearchRequest() {}
    public BookSearchRequest(String query){
        this.query = query;
    }
}
