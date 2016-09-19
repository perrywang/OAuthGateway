package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class BadAccessTokenException extends GatewayException{
    public BadAccessTokenException(String message) {
        super(message);
    }
}
