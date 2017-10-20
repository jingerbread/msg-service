package com.jingerbread.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Slf4j
public class Utils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static LocalDate parseDate(String value) {
        if (value == null) {
            return null;
        }
        try {
            return LocalDate.parse(value, ISO_LOCAL_DATE);
        } catch (Exception e) {
            log.error("Can't parse date {}", value);
        }
        return null;
    }

    public static ReceivedMessage parse(String input) {
        try {
            return MAPPER.readValue(input, ReceivedMessage.class);
        } catch (Exception e) {
            log.error("Can't read {} from json {}", ReceivedMessage.class, input, e);
        }

        return null;
    }

    public static String toString(ReceivedMessage input) {
        try {
            return MAPPER.writeValueAsString(input);
        } catch (Exception e) {
            log.error("Can't convert to json {}", input, e);
        }

        return null;
    }

    public static String readMessage(Object input) {
        String message;
        if (input instanceof byte[]) {
            message = new String((byte[])input, Charset.forName("UTF-8"));
        } else if (input instanceof String) {
            message = (String) input;
        } else {
            log.error("Unknown message type {}", input);
            return null;
        }
        return message;
    }
}
