package org.thinkinghub.gateway.oauth.exception;

/**
 * Normally this exception should not be thrown
 * because the data related user info has returned successfully, otherwise BadOAuthUserInfoException will be thrown
 * instead.
 * <p>
 * but it is triggered in some scenarios such as internal error like code issue, and the field name used to get value
 * is changed by the third-party app
 *
 * @see BadOAuthUserInfoException
 */
@SuppressWarnings("serial")
public class UserIdEmptyException extends GatewayException {
    public String getGWErrorCode() {
        return "GW30004";
    }
}
