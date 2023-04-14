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
    @Length(min = 1, max = 50)
    private String query;

    @Enumerated(EnumType.STRING)
    NaverApiInvokerCommand command;

    public ApiSearchRequest() {}

    public ApiSearchRequest(String query, NaverApiInvokerCommand command) {
        this.query = query;
        this.command = command;
    }
}
