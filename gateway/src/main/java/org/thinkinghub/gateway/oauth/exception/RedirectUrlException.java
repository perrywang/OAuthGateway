package org.thinkinghub.gateway.oauth.exception;

public class RedirectUrlException extends GatewayException {
    public String getGWErrorCode(){
        return "GW30002";
    }
}
