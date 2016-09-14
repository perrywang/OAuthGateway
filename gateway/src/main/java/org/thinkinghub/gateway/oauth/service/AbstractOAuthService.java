package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import com.github.scribejava.core.model.Response;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;

import com.github.scribejava.core.oauth.OAuth20Service;

public abstract class AbstractOAuthService implements OAuthService{
    
	protected abstract OAuth20Service getOAuthService(String state);
	
	public String getAuthorizationUrl(String state) {

		final String authorizationUrl = getOAuthService(state).getAuthorizationUrl();
		return authorizationUrl;
	}

	public GatewayAccessToken getAccessToken(String state, String code) {
		GatewayAccessToken accessToken = null;
		try {
			accessToken = (GatewayAccessToken) getOAuthService(state).getAccessToken(code);
			return accessToken;
		} catch (IOException e) {

		}
		return accessToken;
	}
	
	public abstract Response getResponse(String state, String code);
}
