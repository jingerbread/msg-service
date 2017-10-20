package com.jingerbread.data;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@Getter
public class ReceivedMessage {

    @JsonProperty("message")
    private Message message;

    @JsonProperty("received")
    private Date received;

    @JsonCreator
    public ReceivedMessage(@JsonProperty("message") Message message, @JsonProperty("received") Date received) {
        this.message = message;
        this.received = received;
    }

}
