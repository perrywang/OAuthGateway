package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class CallbackUrlNotFoundException extends GatewayException {
	public String getErrorCode(){
		return "GW10001";
	}
}
