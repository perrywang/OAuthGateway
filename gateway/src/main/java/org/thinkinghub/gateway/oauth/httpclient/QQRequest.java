package org.thinkinghub.gateway.oauth.httpclient;

import java.io.IOException;

import org.thinkinghub.gateway.api.QQApi;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

public class QQRequest {
	private String custCallback = "";

	public QQRequest() {
	}

	public QQRequest(String custCallback) {
		this.custCallback = custCallback;
	}

	public String getCustCallbakc() {
		return this.custCallback;
	}

	public String getAuthorizationUrl() {

		final String authorizationUrl = getOAuthService().getAuthorizationUrl();
		return authorizationUrl;
	}

	public OAuth20Service getOAuthService() {
		final String myApiKey = "101343463";
		final String myApiSecret = "dc492c840680a60535064d1ba50b5872";
		String myCallback = "http://thinkinghub.org/oauth/qq";
		OAuth20Service service = new ServiceBuilder().apiKey(myApiKey).apiSecret(myApiSecret).callback(myCallback)
				.build(QQApi.instance());
		return service;
	}

	public void getAccessToken(String code) {
		try {
			final OAuth2AccessToken accessToken = getOAuthService().getAccessToken(code);
			System.out.println("Got the Access Token!");
			System.out.println("(if your curious it looks like this: " + accessToken + ", 'rawResponse'='"
					+ accessToken.getRawResponse() + "')");

		} catch (IOException e) {

		}
	}
}
