package org.thinkinghub.gateway.oauth.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeixinApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.config.WeixinConfiguration;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.OAuthEncoder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeixinService extends AbstractOAuthService implements OAuthService {
    private final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    @Autowired
    private WeixinConfiguration weixinConfig;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    ResultHandlingService resultHandlingService;

    @PostConstruct
    public void initilize() {
        log.info(weixinConfig.toString());
    }

    protected OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(weixinConfig.getApiKey())
                .apiSecret(weixinConfig.getApiSecret()).callback(OAuthEncoder.encode(weixinConfig.getCallback()))
                .scope(weixinConfig.getScope()).state(state).build(WeixinApi.instance());
        return service;
    }

    @Override
    public String getAuthorizationUrl(String state) {
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("response_type", weixinConfig.getResponseType());
        final String authorizationUrl = getOAuthService(state).getAuthorizationUrl(additionalParams);

        return authorizationUrl;
    }

    @Override
    public Response getResponse(String state, String code) {
        OAuth20Service service = getOAuthService(state);
        GatewayAccessToken accessToken = getAccessToken(state, code);
        eventPublisher.publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
        // send request to get user info
        final OAuthRequest request = new OAuthRequest(Verb.GET, GET_USER_INFO_URL + "?openid=" + accessToken.getUserId(), service);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return response;
    }

    public RetBean getRetBean() {
        return new RetBean();
    }
}
