package com.poolc.springproject.poolcreborn.model.search;

public enum SearchCategory {
    USERNAME("username"), NAME("name"), MAJOR("major"), ISCLUBMEMBER("isClubMember"), ISADMIN("isAdmin");

    private final String value;

    SearchCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
