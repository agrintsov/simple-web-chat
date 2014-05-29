package com.sagr.common;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ResultCodeDeserializer extends JsonDeserializer<ResultCode> {

    @Override
    public ResultCode deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        int value = node.get("code").asInt();
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode() == value) {
                return resultCode;
            }
        }
        return null;
    }

}