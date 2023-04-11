package com.poolc.springproject.poolcreborn.payload.request.book;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
public class BookSearchRequest {
    @Length(min = 1, max = 50)
    private String query;

    private int idx;

    public BookSearchRequest() {}
    public BookSearchRequest(String query){
        this.query = query;
    }

    public BookSearchRequest(String query, int idx) {
        this.query = query;
        this.idx = idx;
    }
}
