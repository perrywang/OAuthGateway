package org.thinkinghub.gateway.core;

import org.thinkinghub.gateway.core.builder.ServiceBuilder;
import org.thinkinghub.gateway.core.builder.api.WeiboApi;
import org.thinkinghub.gateway.core.model.OAuthAccessToken;
import org.thinkinghub.gateway.core.service.OAuthService;

public class WeiboExample {
	public static void main(String args[]) throws Exception{
//        String authCode = getAuthCode();
//		String myApiKey = "";
//		String myApiSecret = "";
    	final String myApiKey = "123050457758183";
    	final String myApiSecret = "123";
		String myCallback = "http://thinkinghub.com/oauth/sina";
        OAuthService service = new ServiceBuilder()
        					.apiKey(myApiKey)
        					.apiSecret(myApiSecret)
        					.callback(myCallback)
        					.build(WeiboApi.getInstance());
        
        
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl();
        System.out.println("authorizationUrl= "+authorizationUrl);
        //同意授权后会重定向
        //http://www.example.com/response&code=CODE
        String code = "CODE";
        
        final OAuthAccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        
//        String accessToken = getAccessToken(authCode);
        
        
//        getService(accessToken);
	}
}
