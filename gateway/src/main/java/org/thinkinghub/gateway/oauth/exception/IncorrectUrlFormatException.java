package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class IncorrectUrlFormatException extends GatewayException {
    public String getGWErrorCode() {
        return "GW10005";
    }
}
