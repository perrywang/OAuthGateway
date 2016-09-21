package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class RedirectUrlException extends GatewayException {
    public RedirectUrlException(Throwable e){
        super(e);
    }
    public String getGWErrorCode(){
        return "GW30002";
    }
}
