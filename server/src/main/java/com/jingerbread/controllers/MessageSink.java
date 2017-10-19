package com.jingerbread.controllers;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


public interface MessageSink {

    String INPUT = "msg_input";

    @Input(INPUT)
    SubscribableChannel input();
}
