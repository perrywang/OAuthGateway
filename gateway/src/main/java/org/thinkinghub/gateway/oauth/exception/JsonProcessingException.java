package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class JsonProcessingException extends GatewayException {
    public JsonProcessingException(String message) {
        super(message);
    }

    public JsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
