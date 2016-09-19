package org.thinkinghub.gateway.oauth.exception;

import lombok.Getter;

@Getter
public class OAuthProcessingException extends GatewayException {

    private static final long serialVersionUID = -253028809896546433L;

    String errCode;// third-party error code
    String errMsg; // third-party error message

    public OAuthProcessingException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
