package com.figure.core.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.figure.core.helper.DateHelper;

import java.io.IOException;
import java.util.Date;

public class Date2StringSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DateHelper.format(date,DateHelper.patterns_masks[2]));
    }

}
