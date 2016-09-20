package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class JsonParsingException extends GatewayException {
    public String getGWErrorCode() {
        return "GW30006";
    }

}
