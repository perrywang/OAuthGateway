package org.thinkinghub.gateway.api;

import org.thinkinghub.gateway.core.extractors.QQOAuth2AccessTokenJsonExtractor;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;

public class QQApi extends DefaultApi20{
	public QQApi(){
	}
	
	private static class InstanceHolder{
		static final QQApi INSTANCE = new QQApi();
	}
	
	public static QQApi instance(){
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String getAuthorizationBaseUrl() {
		return "https://graph.qq.com/oauth2.0/authorize";
	}

	@Override
	public String getAccessTokenEndpoint() {
		return "https://graph.qq.com/oauth2.0/token";
	}
	
	
	@Override
	public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
		return QQOAuth2AccessTokenJsonExtractor.instance();
	}
	
}
