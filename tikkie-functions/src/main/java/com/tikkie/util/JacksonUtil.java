package com.tikkie.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public final class JacksonUtil {

    private JacksonUtil() {

        throw new IllegalStateException("Utility Class");
    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

}
