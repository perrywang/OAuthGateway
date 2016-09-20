package org.thinkinghub.gateway.oauth.exception;

/**
 * This exception is thrown when retrieving user info from the third-party apps such as QQ, Weibo
 * and eventually error code and error message with json format
 * are returned
 */
@SuppressWarnings("serial")
public class BadOAuthUserInfoException extends OAuthProcessingException {
    public BadOAuthUserInfoException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public String getGWErrorCode() {
        return "GW20002";
    }
}
