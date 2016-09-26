package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class UserKeyNotFoundException extends GatewayException {
	public String getErrorCode(){
		return "GW10002";
	}
}
