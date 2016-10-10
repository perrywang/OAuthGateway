package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class UserCancelOAuthException extends OAuthProcessingException{
    public UserCancelOAuthException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public String getGWErrorCode(){
        return "GW20004";
    }
}
