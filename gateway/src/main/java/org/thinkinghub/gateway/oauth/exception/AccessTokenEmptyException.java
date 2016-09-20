package org.thinkinghub.gateway.oauth.exception;

/**
 * This exception is triggered mostly due to communication issue
 * when retrieving access token from the third-party app.
 *
 * @see BadAccessTokenException
 */
@SuppressWarnings("serial")
public class AccessTokenEmptyException extends GatewayException{
    public String getGWErrorCode() {
        return "GW20003";
    }
}
