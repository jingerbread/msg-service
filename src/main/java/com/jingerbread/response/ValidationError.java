package com.jingerbread.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ValidationError {

    private final String field;

    private final String error;

    public ValidationError(String field, String error) {
        this.field = field;
        this.error = error;
    }
}
