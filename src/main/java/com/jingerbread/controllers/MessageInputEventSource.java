package com.jingerbread.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Slf4j
@Component
@EnableBinding(MessageSink.class)
public class MessageInputEventSource {

    @StreamListener(MessageSink.INPUT)
    public void handle(Object input) {//todo add json/msg converter
        if (!(input instanceof byte[])) {
            log.error("Unknown message type {}", input);
            return;
        }
        String message = new String((byte[])input, Charset.forName("UTF-8"));
        log.info("Received message: {}", message);
    }
}
