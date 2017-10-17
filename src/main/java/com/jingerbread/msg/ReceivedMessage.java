package com.jingerbread.msg;


import java.util.Date;

public class ReceivedMessage {

    private final Message message;

    private final Date received;

    public ReceivedMessage(Message message, Date received) {
        this.message = message;
        this.received = received;
    }
}
