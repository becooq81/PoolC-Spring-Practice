package com.poolc.springproject.poolcreborn.api;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum NaverApiInvokerCommand {
    BOOK_SEARCH("v1/search/book", HttpMethod.GET),
    SHOP_SEARCH("v1/search/shop", HttpMethod.POST);

    private final String url;
    private final HttpMethod httpMethod;

    NaverApiInvokerCommand(String url, HttpMethod httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }
}
