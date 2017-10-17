package com.jingerbread.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(CustomSource.class)
public class CustomOutputEventSource {

    @Autowired
    private CustomSource customSource;

    public void sendMessage(String message) {
        customSource.output().send(MessageBuilder.withPayload(message).build());
    }
}
