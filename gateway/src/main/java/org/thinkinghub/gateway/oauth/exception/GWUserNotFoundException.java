package org.thinkinghub.gateway.oauth.exception;

public class GWUserNotFoundException extends GatewayException {

    private static final long serialVersionUID = 3922805047890388140L;

    public GWUserNotFoundException() {
    }
    public String getGWErrorCode(){
        return "GW10004";
    }

}
