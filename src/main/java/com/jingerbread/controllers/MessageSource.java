package com.jingerbread.controllers;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomSource {
    String OUTPUT = "custom_output";

    @Output(CustomSource.OUTPUT)
    MessageChannel output();
}
