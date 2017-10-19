package com.jingerbread.msg.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.io.IOException;

public class JSONReader<T> {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Class<T> clazz;

    public JSONReader(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T readJSON(@Nonnull String input) throws Exception {
        Preconditions.checkNotNull(input, "input");
        try {
            return mapper.readValue(input, clazz);
        } catch (IOException e) {
            throw new Exception("Can't read json", e);
        }
    }
}
