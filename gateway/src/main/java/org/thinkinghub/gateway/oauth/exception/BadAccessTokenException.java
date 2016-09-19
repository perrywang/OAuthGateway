package org.thinkinghub.gateway.oauth.exception;

public class BadAccessTokenException extends GatewayException{
    public BadAccessTokenException(String message) {
        super(message);
    }
}
