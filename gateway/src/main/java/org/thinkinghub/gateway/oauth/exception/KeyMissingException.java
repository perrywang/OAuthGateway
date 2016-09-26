package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class KeyMissingException extends MandatoryParameterMissingException {
	public String getErrorCode(){
		return "GW10002";
	}
}
