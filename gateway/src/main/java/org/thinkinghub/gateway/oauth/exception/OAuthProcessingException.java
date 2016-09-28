package org.thinkinghub.gateway.oauth.exception;

import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class OAuthProcessingException extends GatewayException {

    String errorCode;// third-party error code
    String errorMessage; // third-party error message

    public OAuthProcessingException(String errCode, String errMsg) {
        this.errorCode = errCode;
        this.errorMessage = errMsg;
    }
    
    public String getGWErrorCode(){
    	return "GW29999";
    }
}
