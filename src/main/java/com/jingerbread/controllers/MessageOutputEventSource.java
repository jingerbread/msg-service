package com.jingerbread.controllers;

import com.jingerbread.msg.ReceivedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MessageSource.class)
public class MessageOutputEventSource {

    @Autowired
    private MessageSource messageSource;

    public void sendText(String message) {
        messageSource.output().send(MessageBuilder.withPayload(message).build());
    }

}
