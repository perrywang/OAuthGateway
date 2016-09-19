package org.thinkinghub.gateway.oauth.exception;

public class GatewayException extends RuntimeException {

    private static final long serialVersionUID = 6663442273794973163L;
    
    public String getGWErrorCode(){
    	return "GW99999";
    }

    public GatewayException() {
        super();
    }

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
