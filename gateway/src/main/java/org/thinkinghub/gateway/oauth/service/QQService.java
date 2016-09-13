package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.QQApi;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.config.QQConfiguration;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QQService extends AbstractOAuthService implements OAuthService{
	
	private final String GET_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";
	private final String GET_USER_INFO_URL = "https://graph.qq.com/user/get_user_info";
	
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private QQConfiguration qqConfig;
    
    @Autowired
    ResultHandlingService resultHandlingService;

    @PostConstruct
    public void initilize() {
        log.info(qqConfig.toString());
    }
    
    public OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(qqConfig.getApiKey()).apiSecret(qqConfig.getApiSecret())
                .callback(qqConfig.getCallback()).state(state).build(QQApi.instance());
        return service;
    }

	public String getOpenId(OAuth2AccessToken accessToken, OAuth20Service service) {
		final OAuthRequest request = new OAuthRequest(Verb.GET, GET_OPEN_ID_URL, service);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String rawResponse = null;
		try {
			rawResponse = response.getBody();
		} catch (IOException e) {

		}
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.readTree(rawResponse);
			return node.get("openid").asText();
		} catch (IOException e) {
			throw new GatewayException("Converting RetBean encountered error");
		}
	}
	
	@Override
    public String getUserInfo(String state, String code) {
        OAuth2AccessToken accessToken = null;
        Response response = null;
        String rawResponse = null;
        OAuth20Service service = getOAuthService(state);
        try {
            accessToken = service.getAccessToken(code);
            String openId = getOpenId(accessToken, service);
            eventPublisher.publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
            
            final OAuthRequest request = new OAuthRequest(Verb.GET, GET_USER_INFO_URL+"?oauth_consumer_key="+qqConfig.getApiKey()+"&openid="+openId, service);
            service.signRequest(accessToken, request);
            response = request.send();
            rawResponse = response.getBody();
        } catch (IOException e) {

        } finally {

        }
        String retJson = resultHandlingService.getRetJson(rawResponse, ServiceType.QQ);
        String result = Base64Encoder.getInstance().encode(retJson);
        return result;
    }
	
	public RetBean getRetBean(){
		return new RetBean();
	}
	
}
