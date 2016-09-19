package org.thinkinghub.gateway.oauth.exception;

public class OAuthProcessingException extends GatewayException {

	private static final long serialVersionUID = -253028809896546433L;

	public OAuthProcessingException(String errCode, String errMsg) {
		super(errMsg);
	}

	protected String getErrorCode() {
		return "GW100";
	}

}
