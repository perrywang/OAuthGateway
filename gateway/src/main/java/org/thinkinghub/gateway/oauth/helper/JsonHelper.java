package org.thinkinghub.gateway.oauth.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {
    public static String toJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            throw new org.thinkinghub.gateway.oauth.exception.JsonProcessingException("Exception is thrown when converting object "
                    + obj.toString() + " to Json", e);
        }
    }

    /**
     * return value per the name
     * @param json
     * @param name
     * @return null if the name doesn't exist
     */
    public static String getValue(String json, String name) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(json);
        } catch (IOException e) {
            throw new org.thinkinghub.gateway.oauth.exception.JsonProcessingException("Exception is thrown when getting "
                    + name + " from " + json, e);
        }
        return rootNode.get(name).asText();
    }

}
