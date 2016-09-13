package org.thinkinghub.gateway.oauth.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    public static String toJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
