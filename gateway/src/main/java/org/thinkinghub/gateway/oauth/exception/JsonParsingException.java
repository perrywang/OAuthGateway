package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class JsonParsingException extends GatewayException {
    public JsonParsingException(Throwable e){
        super(e);
    }
    public String getGWErrorCode() {
        return "GW30006";
    }

}
