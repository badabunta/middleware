package com.challenge.middleware.enums;

public enum StatusEnum {
    PENDING("PENDING"), VOTING("VOTING"), VOTED("VOTED");

    public final String value;

    StatusEnum(String value) {
        this.value = value;
    }
}
