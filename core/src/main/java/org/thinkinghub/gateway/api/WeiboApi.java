package org.thinkinghub.gateway.api;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class WeiboApi extends DefaultApi20{
	
	protected WeiboApi(){
	}

	private static class InstanceHolder {
		static final WeiboApi INSTANCE = new WeiboApi();
	}
	
	public static WeiboApi getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	protected String getAuthorizationBaseUrl(){
		return "https://api.weibo.com/oauth2/authorize";
	}
	
	public String getAccessTokenEndpoint() {
        return "https://api.weibo.com/oauth2/access_token";
    }
}