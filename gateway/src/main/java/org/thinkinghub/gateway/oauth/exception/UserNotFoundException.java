package org.thinkinghub.gateway.oauth.exception;

public class UserNotFoundException extends GatewayException {

    private static final long serialVersionUID = 3922805047890388140L;

    public UserNotFoundException(String message) {
        super(message);
    }

}
