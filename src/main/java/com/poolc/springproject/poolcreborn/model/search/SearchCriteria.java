package com.poolc.springproject.poolcreborn.model.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class SearchCriteria {
    private String key;
    private Object value;
    private String operation;
    private String dataOption;

    public SearchCriteria(String key, String operation, Object value){
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

}
