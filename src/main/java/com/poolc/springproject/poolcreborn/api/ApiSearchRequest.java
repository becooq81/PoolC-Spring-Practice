package com.poolc.springproject.poolcreborn.api;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class ApiSearchRequest {

    @Enumerated(EnumType.STRING)
    private ApiType type;
    @NotNull
    private String method;
    @Length(min = 1, max = 50)
    private String query;


    public ApiSearchRequest() {}

    public ApiSearchRequest(ApiType type, String method, String query) {
        this.type = type;
        this.method = method;
        this.query = query;
    }
}
