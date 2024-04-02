package com.figure.core.helper;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class IntegerArrayDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        Integer[] integers = jsonParser.readValueAs(Integer[].class);
        String[] stringArray = Arrays.stream(integers).map(String::valueOf).toArray(String[]::new);
        return String.join(":", stringArray);
    }
}
