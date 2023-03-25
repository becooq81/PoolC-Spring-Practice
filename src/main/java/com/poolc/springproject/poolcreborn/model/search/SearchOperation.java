package com.poolc.springproject.poolcreborn.model.search;

public enum SearchOperation {
    CONTAINS("cn"), DOES_NOT_CONTAIN("nc"), EQUAL("eq"), NOT_EQUAL("ne"), BEGINS_WITH("bw"),
    DOES_NOT_BEGIN_WITH("bn"), ENDS_WITH("ew"), DOES_NOT_END_WITH("en"),
    NULL("nu"), NOT_NULL("nn"), GREATER_THAN("gt"), GREATER_THAN_EQUAL("ge"), LESS_THAN("lt"),
    LESS_THAN_EQUAL("le"), ANY("any"), ALL("all");
    private final String value;
    public static SearchOperation getDataOption(final String dataOption) {
        switch (dataOption) {
            case "all": return ALL;
            case "any": return ANY;
            default: return null;
        }
    }
    SearchOperation(String value) {
        this.value = value;
    }

    public static SearchOperation getSearchOperation(final String input) {
        for (SearchOperation operation : values()) {
            if (input.equals(operation.value)) {
                return operation;
            }
        }
        return null;
    }
}
