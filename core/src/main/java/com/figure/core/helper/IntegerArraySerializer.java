package com.figure.core.helper;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Arrays;

public class IntegerArraySerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String string, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (string == null || string.equals("")) {
            int[] nullArray = new int[0];
            jsonGenerator.writeArray(nullArray, 0, 0);
        } else {
            String[] stringArray = string.split(":");
            int[] integers = Arrays.stream(stringArray).mapToInt(Integer::valueOf).toArray();
            jsonGenerator.writeArray(integers, 0, integers.length);
        }
    }
}
