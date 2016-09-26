package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class ServiceTypeNotFoundException extends GatewayException {
	public String getErrorCode(){
		return "GW10003";
	}
}
