package com.jingerbread.data;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public class Messages {

    private List<Message> values;

    @JsonCreator
    public Messages(List<Message> values) {
        this.values = values;
    }

    @JsonValue
    public List<Message> getValues() {
        return values;
    }
}
