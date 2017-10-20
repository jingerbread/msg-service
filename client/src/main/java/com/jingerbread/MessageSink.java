package com.jingerbread;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageSink {

    String INPUT = "msg_output";

    @Input(INPUT)
    SubscribableChannel input();
}
