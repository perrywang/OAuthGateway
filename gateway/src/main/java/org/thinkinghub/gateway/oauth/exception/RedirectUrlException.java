package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class RedirectUrlException extends GatewayException {
    public String getGWErrorCode(){
        return "GW30002";
    }
}
