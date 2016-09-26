package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class CallbackUrlMissingException extends MandatoryParameterMissingException {
	public String getErrorCode(){
		return "GW10001";
	}
}
