package org.thinkinghub.gateway.core.builder.api;

public class WeiboApi extends DefaultApi{
	
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