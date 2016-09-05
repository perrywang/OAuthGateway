package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.oauth.bean.WeiboAccessToken;
import org.thinkinghub.gateway.oauth.bean.WeiboProperties;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

@Service
public class WeiboService {
	@Autowired
	WeiboProperties weiboConfig;
	
	public WeiboService() {
	}

	private OAuth20Service getOAuthService(String state) {
		OAuth20Service service = new ServiceBuilder()
				.apiKey(weiboConfig.getApiKey())
				.apiSecret(weiboConfig.getApiSecret())
				.callback(weiboConfig.getCallback())
				.state(state)
				.build(WeiboApi.instance());
		return service;
	}

	public String getAuthorizationUrl(String state) {
		final String authorizationUrl = getOAuthService(state).getAuthorizationUrl();
		return authorizationUrl;
	}

	public WeiboAccessToken getResult(String state, String code) {
		OAuth2AccessToken accessToken = null;
		try {
			accessToken = getOAuthService(state).getAccessToken(code);
		} catch (IOException e) {

		} finally {
			
		}
		String accessTokenStr = accessToken.getRawResponse();
		Gson gson = new Gson();
		WeiboAccessToken token = gson.fromJson(accessTokenStr, WeiboAccessToken.class);
		return token;
	}
}
