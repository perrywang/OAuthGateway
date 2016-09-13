package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.config.WeiboConfiguration;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeiboService extends AbstractOAuthService{
	private final String GET_USER_INFO_URL = "https://api.weibo.com/2/users/show.json";
	
    @Autowired
    private WeiboConfiguration weiboConfig;
    
    @Autowired
    private ResultHandlingService resultHandlingService;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public WeiboService() {
    }
    
    @PostConstruct
    public void initilize() {
        log.info(weiboConfig.toString());
    }

    public OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(weiboConfig.getApiKey())
                .apiSecret(weiboConfig.getApiSecret()).callback(weiboConfig.getCallback()).state(state)
                .build(WeiboApi.instance());
        return service;
    }
    
	public String getUserInfo(String state, String code){
        String rawResponse = "";
        try {
            OAuth20Service service = getOAuthService(state);
            GatewayAccessToken accessToken = getAccessToken(state, code);
            eventPublisher.publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
            //for weibo
            final OAuthRequest request = new OAuthRequest(Verb.GET, GET_USER_INFO_URL+"?uid="+accessToken.getUserId(), service);
            service.signRequest(accessToken, request);
            Response response = request.send();
            rawResponse = response.getBody();
        } catch (IOException e) {

        }
        String retJson = resultHandlingService.getRetJson(rawResponse, ServiceType.WEIBO);
        String result = Base64Encoder.getInstance().encode(retJson);
        return result;
    }
	
	public RetBean getRetBean(){
		return new RetBean();
	}
}
