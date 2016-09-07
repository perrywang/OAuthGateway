package org.thinkinghub.gateway.api;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.extractors.WeiboOAuth2AccessTokenJsonExtractor;

public class WeiboApi extends DefaultApi20{
	
	protected WeiboApi(){
	}

	private static class InstanceHolder {
		static final WeiboApi INSTANCE = new WeiboApi();
	}
	
	public static WeiboApi instance(){
		return InstanceHolder.INSTANCE;
	}

	@Override
	public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
		return WeiboOAuth2AccessTokenJsonExtractor.instance();
	}

	protected String getAuthorizationBaseUrl(){
		return "https://api.weibo.com/oauth2/authorize";
	}
	
	public String getAccessTokenEndpoint() {
        return "https://api.weibo.com/oauth2/access_token";
    }
}