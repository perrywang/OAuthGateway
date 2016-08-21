package org.thinkinghub.gateway.core;

import org.thinkinghub.gateway.core.builder.ServiceBuilder;
import org.thinkinghub.gateway.core.builder.api.WeixinApi;
import org.thinkinghub.gateway.core.model.OAuthAccessToken;
import org.thinkinghub.gateway.core.service.OAuthService;

public class WeixinExample {
	public static void main(String args[]) throws Exception{
		//网页授权获取用户基本信息
//		 	String authCode = getAuthCode();
		String appID = "wx5a1a5700fbd61c6b";
		String appsecret = "0ba9181bc53344cbb186f767725044cb";
		String myCallback = "www.thinkinghub.com";
//		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
//		+"appid="+appID+"&redirect_uri=www.thinkinghub.com&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
		
		OAuthService service = new ServiceBuilder()
				.apiKey(appID)
				.apiSecret(appsecret)
				.callback(myCallback)
				.build(WeixinApi.getInstance());
		final String authorizationUrl = service.getWeixinAuthorizationUrl();
		System.out.println(authorizationUrl);
		
		String code = "CODE";
        
        final OAuthAccessToken accessToken = service.getWeixinAccessToken(code);
        System.out.println(accessToken);
//	        String accessToken = getAccessToken(authCode);
//	        getService(accessToken);
	}
}
