package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class GatewayException extends RuntimeException {

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
