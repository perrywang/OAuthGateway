package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.config.WeiboConfiguration;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Service
public class WeiboService {
	private final String GET_USER_INFO_URL = "https://api.weibo.com/2/users/show.json";
	
    @Autowired
    private WeiboConfiguration weiboConfig;

    public WeiboService() {
    }

    private OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(weiboConfig.getApiKey())
                .apiSecret(weiboConfig.getApiSecret()).callback(weiboConfig.getCallback()).state(state)
                .build(WeiboApi.instance());
        return service;
    }

    public String getAuthorizationUrl(String state) {
        final String authorizationUrl = getOAuthService(state).getAuthorizationUrl();
        return authorizationUrl;
    }
    
    public GatewayAccessToken getAccessToken(String state, String code){
    	GatewayAccessToken accessToken = null;
    	try{
    	accessToken = (GatewayAccessToken) getOAuthService(state).getAccessToken(code);
    	}catch(IOException e){
    		
    	}finally{
    		
    	}
    	return accessToken;
    }
    public String getResult(String state, String code){
        Response response = null;
        String rawResponse = "";
        try {
            OAuth20Service service = getOAuthService(state);
            GatewayAccessToken accessToken = getAccessToken(state, code);
            final OAuthRequest request = new OAuthRequest(Verb.GET, GET_USER_INFO_URL+"?uid="+accessToken.getUserId(), service);
            service.signRequest(accessToken, request);
            response = request.send();
            rawResponse = response.getBody();
        } catch (IOException e) {

        } finally {

        }
    	return getRetJson(response.getCode(), rawResponse, ServiceType.WEIBO);
    }
    
    //Johnson will implement this method and replace this one
    public String getRetJson(int code, String rawResponse, ServiceType service){
    	return rawResponse;
    }
}
