package org.thinkinghub.gateway.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.thinkinghub.gateway.oauth.exception.JsonParsingException;

import java.io.IOException;

@Slf4j
public class JsonUtil {
	public static String toJson(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (JsonProcessingException e) {
			log.error("Exception is thrown when converting object " + obj.toString() + " to Json", e);
			throw new JsonParsingException(e);
		}
	}

	/**
	 * Retrieve value per the name
	 *
	 * @param json
	 * @param name
	 * @return null if the name doesn't exist
	 */
	public static String getString(String json, String name) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readTree(json);
		} catch (IOException e) {
			log.error("Exception is thrown when retrieving " + name + " from " + json, e);
			throw new JsonParsingException(e);
		}
		JsonNode node = rootNode.get(name);
		return node == null ? null : node.asText();
	}
}
