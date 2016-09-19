package org.thinkinghub.gateway.oauth.exception;

import lombok.Getter;

@Getter
public class OAuthProcessingException extends GatewayException {

	private static final long serialVersionUID = -253028809896546433L;
	
	String errCode;
	String errMsg;
	
	public OAuthProcessingException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrorCode() {
		return "GW100";
	}
}
