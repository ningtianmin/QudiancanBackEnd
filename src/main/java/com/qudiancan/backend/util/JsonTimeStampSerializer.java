package com.qudiancan.backend.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author NINGTIANMIN
 */
public class JsonTimeStampSerializer extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(value.toString());
        gen.writeString(stringBuilder.delete(stringBuilder.lastIndexOf("."), stringBuilder.length()).toString());
    }
}
