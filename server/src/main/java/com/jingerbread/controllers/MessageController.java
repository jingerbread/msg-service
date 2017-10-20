package com.jingerbread.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.jingerbread.data.JSONReader;
import com.jingerbread.data.Message;
import com.jingerbread.data.Messages;
import com.jingerbread.data.ReceivedMessage;
import com.jingerbread.response.MessageResponseRoot;
import com.jingerbread.response.MessageValidationError;
import com.jingerbread.response.OperationStatus;
import com.jingerbread.response.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Slf4j
@RestController
public class MessageController {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final JSONReader<Messages> jsonReader;

    @Autowired
    private MessageOutputEventSource eventSource;

    @Autowired
    public MessageController() {
        this.jsonReader = new JSONReader<>(Messages.class);
    }

    @RequestMapping(value = "/api/v1/messages", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public MessageResponseRoot addMessages(@RequestBody String body) {
        try {
            return processMessages(body);
        } catch (Exception e) {
            log.error("Can't parse input messages", e);
            return MessageResponseRoot.error(OperationStatus.PARSE_EXCEPTION, e.getMessage());
        }
    }

    private MessageResponseRoot processMessages(@RequestBody String body) throws Exception {
        Messages messages = jsonReader.readJSON(body);

        MessageResponseRoot validationResult = validateMessages(messages.getValues());
        if (!validationResult.isSuccessful()) {
            return validationResult;
        }

        Date receivedDate = new Date();//todo timezones
        List<ReceivedMessage> receivedMessages = messages.getValues().stream()
                .map(m -> new ReceivedMessage(m, receivedDate)).collect(Collectors.toList());

        for (ReceivedMessage receivedMessage : receivedMessages) {
            try {
                String json = MAPPER.writeValueAsString(receivedMessage);
                eventSource.sendText(json);
            } catch (Exception e) {
                log.error("Can't send to kafka msg", e);
            }
        }
        return MessageResponseRoot.success();
    }

    private MessageResponseRoot validateMessages(List<Message> messages) {
        int messageId = 0;
        List<MessageValidationError> validationErrors = new LinkedList<>();

        for (Message message : messages) {
            List<ValidationError> messageErrors = new LinkedList<>();

            validateField("title", message.getTitle()).ifPresent(messageErrors::add);
            validateField("text", message.getText()).ifPresent(messageErrors::add);
            validateField("author", message.getAuthor()).ifPresent(messageErrors::add);
            validateDateField("created", message.getCreated()).ifPresent(messageErrors::add);

            if (!messageErrors.isEmpty()) {
                MessageValidationError validationError = new MessageValidationError(messageId, messageErrors);
                validationErrors.add(validationError);
            }
            messageId++;
        }

        if (!validationErrors.isEmpty()) {
            return MessageResponseRoot.validationError(OperationStatus.VALIDATION_ERROR, validationErrors);
        }

        return MessageResponseRoot.success();
    }

    private Optional<ValidationError> validateField(String field, String value) {
        if (Strings.isNullOrEmpty(value)) {//todo trim
            return Optional.of(new ValidationError(field, "field is empty"));
        }
        return Optional.empty();
    }

    private Optional<ValidationError> validateDateField(String field, String value) {
        if (Strings.isNullOrEmpty(value)) {//todo trim
            return Optional.of(new ValidationError(field, "field is empty"));
        }

        if (parseDate(value)) {
            return Optional.of(new ValidationError(field, "date field should be in format " + ISO_LOCAL_DATE));
        }
        return Optional.empty();
    }

    private boolean parseDate(String value) {
        try {
            LocalDate.parse(value, ISO_LOCAL_DATE);
        } catch (Exception e) {
            return true;
        }
        return false;
    }
}
