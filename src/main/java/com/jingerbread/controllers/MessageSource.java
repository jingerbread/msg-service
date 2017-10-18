package com.jingerbread.controllers;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageSource {
    String OUTPUT = "msg_output";

    @Output(MessageSource.OUTPUT)
    MessageChannel output();
}
