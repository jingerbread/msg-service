package com.jingerbread.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseRoot<T> {

    @Getter
    private final OperationStatus status;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<MessageValidationError> errors;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String error;

    @Getter
    private final int statusCode;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    private MessageResponseRoot(OperationStatus status, T data, List<MessageValidationError> errors, String error) {
        this.status = status;
        this.statusCode = status.code;
        this.data = data;
        this.errors = errors;
        this.error = error;
    }

    public static MessageResponseRoot success() {
        return new MessageResponseRoot<>(OperationStatus.SUCCESS, null, null, null);
    }

    public static <T> MessageResponseRoot<T> success(T data) {
        return new MessageResponseRoot<>(OperationStatus.SUCCESS, data, null, null);
    }

    public static <T> MessageResponseRoot<T> validationError(OperationStatus status, List<MessageValidationError> errors) {
        return new MessageResponseRoot<>(status, null, errors, null);
    }

    public static <T> MessageResponseRoot<T> error(OperationStatus status, String error) {
        return new MessageResponseRoot<>(status, null, null, error);
    }

    public boolean isSuccessful() {
        return status.isSuccessful();
    }
}
