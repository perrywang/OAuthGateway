package org.thinkinghub.gateway.oauth.exception;

public class GatewayException extends RuntimeException {

    private static final long serialVersionUID = 6663442273794973163L;
    
    public GatewayException(String message) {
        super(message);
    }
    
    public GatewayException(Throwable cause) {
        super(cause);
    }
    
    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }

}
