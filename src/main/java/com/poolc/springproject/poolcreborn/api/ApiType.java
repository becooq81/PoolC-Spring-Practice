package com.poolc.springproject.poolcreborn.api;

public enum ApiType {

    BOOK("book"), PRODUCT("shop");
    private final String value;

    ApiType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
