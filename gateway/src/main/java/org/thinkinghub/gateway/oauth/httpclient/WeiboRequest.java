package org.thinkinghub.gateway.oauth.httpclient;

import java.io.IOException;

import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.oauth.bean.WeiboAccessToken;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

public class WeiboRequest {
	private String custCallback = "";
	private String state="";

	public WeiboRequest() {
	}

	public WeiboRequest(String custCallback, String state) {
		this.custCallback = custCallback;
		this.state = state;
	}

	public String getCustCallback() {
		return this.custCallback;
	}
	
	public String getState(){
		return this.state;
	}

	public OAuth20Service getOAuthService() {
		final String myApiKey = "1223382392";
		final String myApiSecret = "69ea74871d5955b4d2f68e430b01810c";
		String myCallback = "http://2e0e1193.ngrok.io/oauth/sina";
		OAuth20Service service = new ServiceBuilder().apiKey(myApiKey).apiSecret(myApiSecret).callback(myCallback).state(getState())
				.build(WeiboApi.getInstance());
		return service;
	}

	public String getAuthorizationUrl() {
		final String authorizationUrl = getOAuthService().getAuthorizationUrl();
		return authorizationUrl;
	}

	public WeiboAccessToken getResult(String code) {
		OAuth2AccessToken accessToken = null;
		try {
			accessToken = getOAuthService().getAccessToken(code);
		} catch (IOException e) {

		} finally {
			
		}
		String accessTokenStr = accessToken.getRawResponse();
		Gson gson = new Gson();
		WeiboAccessToken token = gson.fromJson(accessTokenStr, WeiboAccessToken.class);
		return token;
	}
}
