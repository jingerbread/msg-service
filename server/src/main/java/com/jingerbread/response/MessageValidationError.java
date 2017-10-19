package com.jingerbread.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.List;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MessageValidationError {

    @Getter
    private final long messageId;

    @Getter
    private final List<ValidationError> validationErrors;

    public MessageValidationError(long messageId, List<ValidationError> validationErrors) {
        this.messageId = messageId;
        this.validationErrors = validationErrors;
    }
}
