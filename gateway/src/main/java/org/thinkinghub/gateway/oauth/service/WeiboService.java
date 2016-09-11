package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.config.WeiboConfiguration;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.util.MD5Base64Encoder;

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
    
    @Autowired
    private ResultHandlingService resultHandlingService;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

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
    
    public String getResult(String state, String code){
        Response response = null;
        String rawResponse = "";
        try {
            OAuth20Service service = getOAuthService(state);
            GatewayAccessToken accessToken = (GatewayAccessToken) getOAuthService(state).getAccessToken(code);
            eventPublisher.publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
            final OAuthRequest request = new OAuthRequest(Verb.GET, GET_USER_INFO_URL+"?uid="+accessToken.getUserId(), service);
            service.signRequest(accessToken, request);
            response = request.send();
            rawResponse = response.getBody();
        } catch (IOException e) {

        } finally {

        }
        String retJson = resultHandlingService.getRetJson(rawResponse, ServiceType.WEIBO);
        MD5Base64Encoder.getInstance();
		String result = MD5Base64Encoder.encode(retJson);
        return result;
    }
}
