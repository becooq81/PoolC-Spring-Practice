package com.poolc.springproject.poolcreborn.api;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class NaverApiInvokerCommand {
    private ApiType type;
    private String address;
    private String method;
    private String query;

    public NaverApiInvokerCommand(ApiType type, String method, String query) {
        this.type = type;
        this.address = "https://openapi.naver.com/";
        this.method = method;
        this.query = query;
    }

    public NaverApiInvokerCommand() {}

    public NaverApiInvokerCommand(ApiSearchRequest searchRequest) {
        this.type = searchRequest.getType();
        this.address = "https://openapi.naver.com/";
        this.method = searchRequest.getMethod();
        this.query = searchRequest.getQuery();
    }


}
