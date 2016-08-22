package org.thinkinghub.gateway.core.builder.api;


public class WeixinApi extends DefaultApi{

	public WeixinApi(){
	}
	
	private static class InstanceHolder {
		static final WeixinApi INSTANCE = new WeixinApi();
	}
	
	public static WeixinApi getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	@Override
	protected String getAuthorizationBaseUrl() {
		// TODO Auto-generated method stub
		return "https://open.weixin.qq.com/connect/oauth2/authorize";
	}

	@Override
	public String getAccessTokenEndpoint() {
		return "https://api.weixin.qq.com/sns/oauth2/access_token";
	}
	
}
