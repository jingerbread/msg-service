package com.jingerbread.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MessageSource.class)
public class CustomOutputEventSource {

    @Autowired
    private MessageSource messageSource;

    public void sendMessage(String message) {
        messageSource.output().send(MessageBuilder.withPayload(message).build());
    }
}
