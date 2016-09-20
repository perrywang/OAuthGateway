package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class BadAccessTokenException extends OAuthProcessingException{
    public BadAccessTokenException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public String getGWErrorCode(){
        return "GW20001";
    }
}
