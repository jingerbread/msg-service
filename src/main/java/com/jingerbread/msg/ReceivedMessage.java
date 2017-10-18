package com.jingerbread.msg;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@Getter
public class ReceivedMessage {

    @JsonProperty("message")
    private Message message;

    @JsonProperty("received")
    private Date received;

    public ReceivedMessage(Message message, Date received) {
        this.message = message;
        this.received = received;
    }

}
