package com.sagr.common;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * Created by Sasha on 24.05.14.
 */
public class ResultCodeDeserializer extends JsonDeserializer<ResultCode> {

    @Override
    public ResultCode deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int value = node.get("code").asInt();
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode() == value) {
                return resultCode;
            }
        }
        throw new JsonParseException("No suitable ResultCode found for code = " + value, jp.getTokenLocation());
    }

}