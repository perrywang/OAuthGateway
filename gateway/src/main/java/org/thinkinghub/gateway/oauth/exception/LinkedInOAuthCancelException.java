package org.thinkinghub.gateway.oauth.exception;

public class LinkedInOAuthCancelException extends OAuthProcessingException {
    public LinkedInOAuthCancelException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public String getGWErrorCode(){
        return "GW20004";
    }
}
