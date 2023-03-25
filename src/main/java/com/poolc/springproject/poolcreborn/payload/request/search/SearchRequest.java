package com.poolc.springproject.poolcreborn.payload.request.search;

import com.poolc.springproject.poolcreborn.model.search.SearchCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder(toBuilder = true)
public class SearchRequest {
    @NotEmpty
    private SearchCategory searchCategory;
    @NotEmpty
    private String keyword;

    public SearchRequest(SearchCategory searchCategory, String keyword) {
        this.searchCategory = searchCategory;
        this.keyword = keyword;
    }

    public SearchRequest() {}
}
