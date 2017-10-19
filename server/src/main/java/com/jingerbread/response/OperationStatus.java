package com.jingerbread.response;


public enum OperationStatus {
    SUCCESS(0),
    PARSE_EXCEPTION(1),
    VALIDATION_ERROR(2),
    INTERNAL_ERROR(99);

    public final int code;

    OperationStatus(int code) {
        this.code = code;
    }

    public boolean isSuccessful() {
        return this == SUCCESS;
    }
}
